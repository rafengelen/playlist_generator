package fact.it.playlistservice;

import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.dto.PreferenceResponse;
import fact.it.playlistservice.dto.SongDto;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.model.PlaylistSong;
import fact.it.playlistservice.repository.PlaylistRepository;
import fact.it.playlistservice.service.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceUnitTests {
    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(playlistService, "preferenceServiceBaseUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(playlistService, "songlibraryServiceBaseUrl", "http://localhost:8082");
    }
    @Test
    public void testGeneratePlaylist(){
        //ARRANGE
        String user ="user";

        PlaylistSong playlistSong1 = new PlaylistSong(1L,"code1");
        PlaylistSong playlistSong2 = new PlaylistSong(2L,"code2");

        List<PlaylistSong> songs = Arrays.asList(playlistSong1, playlistSong2);

        Playlist playlist = new Playlist(1L, user, "code",true,  songs);

        PreferenceResponse preferenceResponse = new PreferenceResponse("genre",user,"code");

        when(playlistRepository.save(any(Playlist.class))).thenReturn(playlist);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(),  any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PreferenceResponse[].class)).thenReturn(Mono.just(new PreferenceResponse[]{preferenceResponse}));
        when(responseSpec.bodyToMono(String[].class)).thenReturn(Mono.just(new String[]{"code1", "code2"}));

        //ACT
        boolean response = playlistService.generatePlaylist(user, true);

        //ASSERT
        assertTrue(response);
        verify(playlistRepository, times(1)).save(any(Playlist.class));
    }

    @Test
    public void testGetAllPlaylistsByUserId() {
        // ARRANGE
        String user = "user";

        PlaylistSong playlistSong1 = new PlaylistSong(1L, "code1");
        PlaylistSong playlistSong2 = new PlaylistSong(2L, "code2");

        List<PlaylistSong> songs1 = Arrays.asList(playlistSong1, playlistSong2);

        PlaylistSong playlistSong3 = new PlaylistSong(3L, "code3");
        PlaylistSong playlistSong4 = new PlaylistSong(4L, "code4");

        List<PlaylistSong> songs2 = Arrays.asList(playlistSong3, playlistSong4);

        Playlist playlist1 = new Playlist(1L, user, "code5", true, songs1);
        Playlist playlist2 = new Playlist(2L, user, "code6", false, songs2);


        List<Playlist> playlists = Arrays.asList(playlist1, playlist2);

        when(playlistRepository.findAllByUserId(user)).thenReturn(playlists);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(SongDto.class))
                .thenReturn(Mono.just(new SongDto("title1", "band1", "genre1", "code1", "linkYoutube1", "linkSpotify1")))
                .thenReturn(Mono.just(new SongDto("title2", "band2", "genre2", "code2", "linkYoutube2", "linkSpotify2")))
                .thenReturn(Mono.just(new SongDto("title3", "band3", "genre3", "code3", "linkYoutube3", "linkSpotify3")))
                .thenReturn(Mono.just(new SongDto("title4", "band4", "genre4", "code4", "linkYoutube4", "linkSpotify4")));

        // ACT
        List<PlaylistResponse> playlistResponses = playlistService.getAllPlaylistsByUserId(user);

        // ASSERT
        assertEquals(2, playlistResponses.size());

        PlaylistResponse playlistResponse1 = playlistResponses.get(0);
        assertEquals("code5", playlistResponse1.getCode());
        assertNotNull(playlistResponse1.getSongList());
        assertEquals(2, playlistResponse1.getSongList().size());
        assertEquals("code1", playlistResponse1.getSongList().get(0).getCode());
        assertEquals("code2", playlistResponse1.getSongList().get(1).getCode());
        assertTrue(playlistResponse1.isPublic());

        PlaylistResponse playlistResponse2 = playlistResponses.get(1);
        assertEquals("code6", playlistResponse2.getCode());
        assertNotNull(playlistResponse2.getSongList());
        assertEquals(2, playlistResponse2.getSongList().size());
        assertEquals("code3", playlistResponse2.getSongList().get(0).getCode());
        assertEquals("code4", playlistResponse2.getSongList().get(1).getCode());
        assertFalse(playlistResponse2.isPublic());
    }
    @Test
    public void testGetAllPublicPlaylists(){
        //ARRANGE
        PlaylistSong playlistSong1 = new PlaylistSong(1L, "code1");
        PlaylistSong playlistSong2 = new PlaylistSong(2L, "code2");

        List<PlaylistSong> songs1 = Arrays.asList(playlistSong1, playlistSong2);

        PlaylistSong playlistSong3 = new PlaylistSong(3L, "code3");
        PlaylistSong playlistSong4 = new PlaylistSong(4L, "code4");

        List<PlaylistSong> songs2 = Arrays.asList(playlistSong3, playlistSong4);

        Playlist playlist1 = new Playlist(1L, "user1", "code5", true, songs1);
        Playlist playlist2 = new Playlist(2L, "user2", "code6", true, songs2);

        List<Playlist> playlists = Arrays.asList(playlist1, playlist2);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(playlistRepository.findAllByIsPublicTrue()).thenReturn(playlists);
        when(responseSpec.bodyToMono(SongDto.class))
                .thenReturn(Mono.just(new SongDto("title1", "band1", "genre1", "code1", "linkYoutube1", "linkSpotify1")))
                .thenReturn(Mono.just(new SongDto("title2", "band2", "genre2", "code2", "linkYoutube2", "linkSpotify2")))
                .thenReturn(Mono.just(new SongDto("title3", "band3", "genre3", "code3", "linkYoutube3", "linkSpotify3")))
                .thenReturn(Mono.just(new SongDto("title4", "band4", "genre4", "code4", "linkYoutube4", "linkSpotify4")));
        //ACT
        List<PlaylistResponse> response = playlistService.getAllPublicPlaylists();
        // ASSERT
        assertEquals("user1",response.get(0).getUserId());
        assertEquals("code5", response.get(0).getCode());
        assertTrue(response.get(0).isPublic());

        assertEquals("user2",response.get(1).getUserId());
        assertEquals("code6", response.get(1).getCode());
        assertTrue(response.get(1).isPublic());
    }
    @Test
    public void testDeletePlaylist(){
        //ARRANGE
        String code = "code";
        String user = "user";
        PlaylistSong playlistSong1 = new PlaylistSong(1L,"code1");
        PlaylistSong playlistSong2 = new PlaylistSong(2L,"code2");

        List<PlaylistSong> songs = Arrays.asList(playlistSong1, playlistSong2);
        Playlist playlist = new Playlist(1L, user, code, true,songs);

        when(playlistRepository.findByCode(code)).thenReturn(playlist);

        // ACT
        playlistService.deletePlaylist(code, user);

        // ASSERT
        verify(playlistRepository, times(1)).deleteByCode(code);
    }
}

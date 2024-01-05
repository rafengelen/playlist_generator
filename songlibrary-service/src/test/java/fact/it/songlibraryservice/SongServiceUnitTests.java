
package fact.it.songlibraryservice;

import fact.it.songlibraryservice.dto.SongResponse;
import fact.it.songlibraryservice.model.Song;
import fact.it.songlibraryservice.repository.SongRepository;
import fact.it.songlibraryservice.service.SongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SongServiceUnitTests {
    @InjectMocks
    private SongService songService;

    @Mock
    private SongRepository songRepository;



    @Test
    public void testGetSongCodesByGenre() {
        // Arrange
        String genre = "genre";

        Song song1 = new Song(1L, "title1", "band1", genre, "code1", "linkyt1", "linkspot1");
        Song song2 = new Song(2L, "title2", "band2", genre, "code2", "linkyt2", "linkspot2");
        List<Song> songs = Arrays.asList(song1,song2);

        when(songRepository.findAllByGenre(genre)).thenReturn(songs);

        // Act
        List<String> songCodesResponse = songService.getSongCodesByGenre(genre);

        // Assert
        assertEquals(2, songCodesResponse.size());
        assertEquals("code1", songCodesResponse.get(0));
        assertEquals("code2", songCodesResponse.get(1));

        verify(songRepository, times(1)).findAllByGenre(genre);
    }
    @Test
    public void testGetSongByCode() {
        // Arrange
        String code = "code1";
        Song song = new Song(2L, "title", "band", "genre", code, "linkyt", "linkspot");

        when(songRepository.findByCode(code)).thenReturn(song);

        // Act
        SongResponse songResponse = songService.getSongByCode(code);

        // Assert
        assertEquals(song.getTitle(), songResponse.getTitle());
        assertEquals(song.getBand(), songResponse.getBand());
        assertEquals(song.getGenre(), songResponse.getGenre());
        assertEquals(song.getCode(), songResponse.getCode());
        assertEquals(song.getLinkYoutube(), songResponse.getLinkYoutube());
        assertEquals(song.getLinkSpotify(), songResponse.getLinkSpotify());

        verify(songRepository, times(1)).findByCode(code);
    }



}

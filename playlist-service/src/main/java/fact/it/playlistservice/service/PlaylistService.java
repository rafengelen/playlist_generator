package fact.it.playlistservice.service;

import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.dto.SongDto;
import fact.it.playlistservice.dto.PreferenceResponse;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.model.PlaylistSong;
import fact.it.playlistservice.repository.PlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final WebClient webClient;

    @Value("${preferenceservice.baseurl}")
    private String preferenceServiceBaseUrl;

    @Value("${songlibraryservice.baseurl}")
    private String songlibraryServiceBaseUrl;

    public boolean generatePlaylist(String userId){
        Playlist playlist = new Playlist();
        playlist.setCode(UUID.randomUUID().toString());

        playlist.setUserId(userId);

        PreferenceResponse[] preferenceResonses = webClient.get()
                .uri("http://" + preferenceServiceBaseUrl + "/api/preference/user",
                        uriBuilder -> uriBuilder.queryParam("userId", userId).build())
                .retrieve()
                .bodyToMono(PreferenceResponse[].class)
                .block();

        List<PlaylistSong> songList = new ArrayList<>();
        for (PreferenceResponse pref : preferenceResonses){
            System.out.println(pref.getName());
        }

        for (PreferenceResponse preference : preferenceResonses){
            String[] codeArray = webClient.get()
                    .uri("http://"+ songlibraryServiceBaseUrl +"/api/song/code",
                            uriBuilder -> uriBuilder.queryParam("genre", preference.getName()).build())
                    .retrieve()
                    .bodyToMono(String[].class)
                    .block();

            if( codeArray!= null || codeArray.length!=0 ) {

                List<PlaylistSong> playlistSongs = Arrays.stream(codeArray)
                        .map(code -> new PlaylistSong(code))
                        .collect(Collectors.toList());



                songList.addAll(playlistSongs);
            }
        }

        if (!songList.isEmpty()){
            playlist.setPlaylistSongList(songList);
            playlistRepository.save(playlist);
            return true;
        } else {
            return false;
        }
    }

    public List<PlaylistResponse> getAllPlaylistsByUserId(String userId){
        return playlistRepository.findAllByUserId(userId)
                .stream()
                .map(playlist -> new PlaylistResponse(
                        playlist.getCode(),
                        playlist.getPlaylistSongList()
                )).toList();
    }
    /*public PlaylistResponse getPlaylistByCode(String code){
        Playlist playlist = playlistRepository.findByCode(code);

        return new PlaylistResponse(playlist.getCode(),playlist.getPlaylistSongList());
    }*/


    public void deletePlaylist(String code, String userId) {
        Playlist playlist = playlistRepository.findByCode(code);
        if (userId.equals(playlist.getUserId())){
            playlistRepository.deleteByCode(code);
        }

    }


}

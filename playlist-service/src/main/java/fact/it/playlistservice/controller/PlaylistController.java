package fact.it.playlistservice.controller;

import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.dto.SongDto;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String generatePlaylist(@RequestParam String userId) {
        boolean hasWorked = playlistService.generatePlaylist(userId);
        return (hasWorked ? "Playlist generated successfully" : "Playlist could not generate");
    }

    @GetMapping("user")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistResponse> getAllPlaylistsByUserId(@RequestParam String userId) {
        return playlistService.getAllPlaylistsByUserId(userId);

    }

    @GetMapping("/code")
    @ResponseStatus(HttpStatus.OK)
    public PlaylistResponse getAllPlaylistsByCode(@RequestParam String code) {
        return playlistService.getPlaylistByCode(code);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void deletePlaylist(@RequestParam String code) {
        playlistService.deletePlaylist(code);
    }

}

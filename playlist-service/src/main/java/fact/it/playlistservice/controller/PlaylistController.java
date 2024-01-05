package fact.it.playlistservice.controller;

import fact.it.playlistservice.dto.PlaylistResponse;

import fact.it.playlistservice.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import fact.it.playlistservice.decoder.JwtDecoder;

import java.util.List;


@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String generatePlaylist(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String userId = JwtDecoder.getUserId(jwtToken);
        boolean hasWorked = playlistService.generatePlaylist(userId);
        return (hasWorked ? "Playlist generated successfully" : "Playlist could not generate");
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistResponse> getAllPlaylistsByUserId(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String userId = JwtDecoder.getUserId(jwtToken);
        return playlistService.getAllPlaylistsByUserId(userId);

    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void deletePlaylist(@RequestParam String code, @RequestHeader("Authorization") String authorizationHeader) {

        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String userId = JwtDecoder.getUserId(jwtToken);
        playlistService.deletePlaylist(code, userId);
    }



}

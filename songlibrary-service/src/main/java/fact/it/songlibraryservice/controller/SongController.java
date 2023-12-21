package fact.it.songlibraryservice.controller;


import fact.it.songlibraryservice.dto.SongResponse;
import fact.it.songlibraryservice.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/song")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping("/code")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSongCodesByGenre
    (@RequestParam String genre) {
        return songService.getSongCodesByGenre(genre);
    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public SongResponse getSongByCode
            (@RequestParam String code) {
        return songService.getSongByCode(code);
    }
}

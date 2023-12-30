package fact.it.songlibraryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongRequest {
    private String title;
    private String band;
    private String genre;
    private String linkYoutube;
    private String linkSpotify;
}

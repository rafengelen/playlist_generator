package fact.it.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongResponse {
    private String title;
    private String band;
    private String genre;

    private String linkYoutube;
    private String linkSpotify;


}

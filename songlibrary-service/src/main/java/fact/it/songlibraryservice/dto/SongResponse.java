package fact.it.songlibraryservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongResponse {
    private String title;
    private String band;
    private String genre;
    private String code;
    //@Nullable
    private String linkYoutube;
    //@Nullable
    private String linkSpotify;
}

package fact.it.playlistservice.dto;

import fact.it.playlistservice.model.PlaylistSong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistResponse {
    private String code;
    private String userId;
    private List<SongDto> songList;
}

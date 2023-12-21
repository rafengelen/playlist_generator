package fact.it.playlistservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "playlistsong")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    public PlaylistSong(String code) {
        this.code =code;
    }


}

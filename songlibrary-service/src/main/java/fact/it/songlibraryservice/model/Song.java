package fact.it.songlibraryservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;


@Entity
@Table(name = "song")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String band;
    private String genre;
    private String code;
    @Column(nullable = true)
    private String linkYoutube;

    @Column(nullable = true)
    private String linkSpotify;
}

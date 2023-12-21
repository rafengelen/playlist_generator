package fact.it.playlistservice.repository;

import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.model.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAllByUserId(String userId);

    Playlist findByCode(String code);

    void deleteByCode(String code);
}

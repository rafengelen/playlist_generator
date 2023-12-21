package fact.it.songlibraryservice.repository;

import fact.it.songlibraryservice.model.Song;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByGenre(String genre);
    Song findByCode(String code);
}


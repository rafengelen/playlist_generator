package fact.it.songlibraryservice.service;

import fact.it.songlibraryservice.dto.SongRequest;
import fact.it.songlibraryservice.dto.SongResponse;
import fact.it.songlibraryservice.model.Song;
import fact.it.songlibraryservice.repository.SongRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    @PostConstruct
    public void loadData() {
        if(songRepository.count() == 0){
            Song song1 = new Song();
            song1.setTitle("PA PA YA!!");
            song1.setBand("BABYMETAL");
            song1.setGenre("Metal");
            song1.setLinkYoutube("https://www.youtube.com/watch?v=oO7Y8NsnkRg");
            song1.setLinkSpotify("https://open.spotify.com/track/0hnTPdm3w4MzlBKc6ViOIP?si=b4af207915f64de1");
            song1.setCode(UUID.randomUUID().toString());

            Song song2 = new Song();
            song2.setTitle("Livin' On A Prayer");
            song2.setBand("Bon Jovi");
            song2.setGenre("Rock");
            song2.setLinkSpotify("https://open.spotify.com/track/37ZJ0p5Jm13JPevGcx4SkF?si=71cbdab733d14d2b");
            song2.setCode(UUID.randomUUID().toString());

            Song song3 = new Song();
            song3.setTitle("Astronomia");
            song3.setBand("Vicetone");
            song3.setGenre("EDM");
            song3.setLinkYoutube("https://www.youtube.com/watch?v=iLBBRuVDOo4");
            song3.setCode(UUID.randomUUID().toString());

            Song song4 = new Song();
            song4.setTitle("Lose Yourself");
            song4.setBand("Bon Jovi");
            song4.setGenre("Rock");
            song4.setLinkSpotify("https://open.spotify.com/track/0v1XpBHnsbkCn7iJ9Ucr1l?si=fddfb65ff3c64b1d");
            song4.setLinkYoutube("https://www.youtube.com/watch?v=vx2u5uUu3DE");
            song4.setCode(UUID.randomUUID().toString());

            Song song5 = new Song();
            song5.setTitle("It's My Life");
            song5.setBand("Bon Jovi");
            song5.setGenre("Rock");
            song5.setLinkSpotify("https://open.spotify.com/track/0v1XpBHnsbkCn7iJ9Ucr1l?si=fddfb65ff3c64b1d");
            song5.setLinkYoutube("https://www.youtube.com/watch?v=vx2u5uUu3DE");
            song5.setCode(UUID.randomUUID().toString());

            songRepository.save(song1);
            songRepository.save(song2);
            songRepository.save(song3);
            songRepository.save(song4);

            Song withoutMe = new Song();
            withoutMe.setTitle("Without Me");
            withoutMe.setBand("Eminem");
            withoutMe.setGenre("Hip hop");
            withoutMe.setCode(UUID.randomUUID().toString());
            songRepository.save(withoutMe);
       
            Song loseYourself = new Song();
            loseYourself.setTitle("Lose Yourself");
            loseYourself.setBand("Eminem");
            loseYourself.setGenre("Hip hop");
            loseYourself.setCode(UUID.randomUUID().toString());
            songRepository.save(loseYourself);

            // Eminem - When I'm Gone
            Song whenImGone = new Song();
            whenImGone.setTitle("When I'm Gone");
            whenImGone.setBand("Eminem");
            whenImGone.setGenre("Hip hop");
            whenImGone.setCode(UUID.randomUUID().toString());
            songRepository.save(whenImGone);

            // Eminem - Not Afraid
            Song notAfraid = new Song();
            notAfraid.setTitle("Not Afraid");
            notAfraid.setBand("Eminem");
            notAfraid.setGenre("Hip hop");
            notAfraid.setCode(UUID.randomUUID().toString());
            songRepository.save(notAfraid);
            // Eminem - Venom
            Song venom = new Song();
            venom.setTitle("Venom");
            venom.setBand("Eminem");
            venom.setGenre("Hip hop");
            venom.setCode(UUID.randomUUID().toString());
            songRepository.save(venom);
        }


    }


    @Transactional(readOnly = true)
    public List<String> getSongCodesByGenre(String genre) {

        return songRepository.findAllByGenre(genre).stream()
                .map(Song::getCode)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SongResponse getSongByCode(String code) {
        Song song = songRepository.findByCode(code);
        return SongResponse.builder()
                .title(song.getTitle())
                .band(song.getBand())
                .code(song.getCode())
                .genre(song.getGenre())
                .linkSpotify(song.getLinkSpotify())
                .linkYoutube(song.getLinkYoutube())
                .build();
    }

    public void createSong(SongRequest songRequest) {
        Song song = new Song();
        song.setCode(UUID.randomUUID().toString());
        song.setBand(songRequest.getBand());
        song.setTitle(songRequest.getTitle());
        song.setLinkSpotify(songRequest.getLinkSpotify());
        song.setLinkYoutube(songRequest.getLinkYoutube());
        song.setGenre(songRequest.getGenre());
        songRepository.save(song);

    }
}

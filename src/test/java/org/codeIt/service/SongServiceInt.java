package org.codeIt.service;


import org.codeIt.SongLibraryApplication;
import org.codeIt.model.Artist;
import org.codeIt.model.GenreType;
import org.codeIt.model.Song;
import org.codeIt.model.exceptions.SongNotFoundException;
import org.codeIt.repository.ArtistRepository;
import org.codeIt.repository.SongRepository;
import org.codeIt.service.SongService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SongLibraryApplication.class)
@Transactional
public class SongServiceInt {

    @Autowired
    private SongService songService;

    @Autowired
    private SongRepository songRepository;

    Song song;

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    public void init() {
        Artist artist = new Artist("elena", "elena", LocalDate.of(1987, 4, 27), "macedonian", new ArrayList<>());
        artistRepository.saveAndFlush(artist);

        song = new Song();
        song.setTitle("pesna1");
        song.setGenre(GenreType.POP);
        song.setReleaseDate(LocalDate.of(2022, 12, 12));
        song.setDuration(100);

        song.setArtist(artist);
        songRepository.saveAndFlush(song);


        Artist artist2 = new Artist("karolina", "karolina", LocalDate.of(1980, 4, 27), "macedonian", new ArrayList<>());
        artistRepository.saveAndFlush(artist2);
        song = new Song();
        song.setTitle("pesna2");
        song.setGenre(GenreType.POP);
        song.setReleaseDate(LocalDate.of(2022, 12, 12));
        song.setDuration(400);

        song.setArtist(artist2);

        songRepository.saveAndFlush(song);
    }

    @AfterEach
    public void cleanUp() {
        songRepository.delete(song);
    }


    @Test
    @Transactional
    public void assertThatSongMustExist() {
        Long songId = song.getId();
        Song maybeSong = songService.findById(songId);
        Assertions.assertNotNull(maybeSong);
        Assertions.assertEquals(maybeSong.getArtist(), song.getArtist());
        Assertions.assertThrows(SongNotFoundException.class, () -> {
            songService.findById(99L);
        });
    }

    @Test
    @Transactional
    public void assertThatSongsListExists() {
        List<Song> maybeSongs = songService.listAll();
        Assertions.assertNotNull(maybeSongs);
        maybeSongs.stream().forEach(song1 -> {
            System.out.println(song1.getTitle());
        });
        Assertions.assertEquals(2, maybeSongs.size());
    }

    @Test
    @Transactional
    public void assertGetSongsBetweenDurationWorks() {
        List<Song> songList = songRepository.getSongsBetweenDuration();
        Assertions.assertNotNull(songList);
        Assertions.assertEquals(1, songList.size());
        Assertions.assertEquals("pesna2", songList.get(0).getTitle());
    }


    @Test
    @Transactional
    public void assertThatSongDeleteMethodWorks() {
        Long songId = song.getId();
        songService.delete(songId);

        Assertions.assertThrows(SongNotFoundException.class, () -> {
            songService.findById(songId);
        }, "SongNotFoundException should be thrown when artist is not found");
    }
}

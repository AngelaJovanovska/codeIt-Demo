package org.codeIt.service;

import org.codeIt.SongLibraryApplication;
import org.codeIt.model.Artist;
import org.codeIt.model.GenreType;
import org.codeIt.model.Song;
import org.codeIt.model.exceptions.ArtistNotFoundException;
import org.codeIt.repository.ArtistRepository;
import org.codeIt.service.ArtistService;
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
public class ArtistServiceIntTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistService artistService;

    Artist artist;

    @BeforeEach
    public void init() {
        artist = new Artist();
        artist.setRealName("johndoe");
        artist.setArtisticName("johnsnow");
        artist.setDateOfBirth(LocalDate.of(1999, 12, 12));
        artist.setNationality("english");
        artist.setSongCollection(new ArrayList<>());
        Song song1 = new Song("pesna1", 190, LocalDate.of(2020, 12, 20), GenreType.POP, artist);
        Song song2 = new Song("pesna2", 180, LocalDate.of(2010, 8, 11), GenreType.FOLK, artist);


        artist.getSongCollection().add(song1);
        artist.getSongCollection().add(song2);
        artistRepository.saveAndFlush(artist);
//
        artist = new Artist();
        artist.setRealName("aaa");
        artist.setArtisticName("aaa");
        artist.setDateOfBirth(LocalDate.of(1989, 12, 12));
        artist.setNationality("macedonian");
        artist.setSongCollection(new ArrayList<>());
        Song song3 = new Song("pesna3", 190, LocalDate.of(2020, 12, 20), GenreType.POP, artist);
        Song song4 = new Song("pesna4", 180, LocalDate.of(2010, 8, 11), GenreType.FOLK, artist);


        artist.getSongCollection().add(song1);
        artist.getSongCollection().add(song2);
        artistRepository.saveAndFlush(artist);
    }

    @AfterEach
    public void cleanUpArtists() {
        artistRepository.deleteAll();

    }

    @Test
    @Transactional
    public void assertThatArtistMustExist() {
        Long artistId = artist.getId();
        Artist maybeArtist = artistService.findById(artistId);
        Assertions.assertNotNull(maybeArtist);

        Assertions.assertEquals(maybeArtist.getSongCollection().size(), artist.getSongCollection().size());

        Assertions.assertThrows(ArtistNotFoundException.class, () -> {
            artistService.findById(99L);
        });

    }


    @Test
    @Transactional
    public void assertThatArtistsListExists() {
        List<Artist> maybeArtists = artistService.findAll();
        Assertions.assertNotNull(maybeArtists);
        maybeArtists.stream().forEach(artist1 -> {
            System.out.println(artist1.getArtisticName());
        });
        Assertions.assertEquals(2, maybeArtists.size());
    }

    //
    @Test
    @Transactional
    public void assertThatFilterByDateOfBirthAndNationalityWorks() {
        List<Artist> artistList = artistService.filterByDateOfBirthAndNationality(LocalDate.of(1999, 1, 1), "macedonian");
        Assertions.assertNotNull(artistList);
        Assertions.assertEquals(1, artistList.size());
        Assertions.assertEquals("aaa", artistList.get(0).getArtisticName());
    }


    @Test
    @Transactional
    public void assertThatArtistDeleteMethodWorks() {
        Long artistId = artist.getId();
        artistService.delete(artistId);

        Assertions.assertThrows(ArtistNotFoundException.class, () -> {
            artistService.findById(artistId);
        }, "ArtistNotFoundException should be thrown when artist is not found");
    }

}
package org.codeIt.service;

import org.codeIt.SongLibraryApplication;
import org.codeIt.model.*;
import org.codeIt.model.exceptions.PlaylistNotFoundException;
import org.codeIt.repository.ArtistRepository;
import org.codeIt.repository.PlaylistRepository;
import org.codeIt.repository.SongRepository;
import org.codeIt.service.PlaylistService;
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
public class PlaylistServiceIntTest {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistRepository playlistRepository;


    Playlist playlist;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    SongRepository songRepository;


    @BeforeEach
    public void init() {

        Artist artist = new Artist("elena", "elena", LocalDate.of(1987, 4, 27), "macedonian", new ArrayList<>());
        artistRepository.saveAndFlush(artist);


        playlist = new Playlist();
        playlist.setName("pleylista");
        playlist.setStatus(StatusType.PUBLIC);
        playlist.setDateOfCreation(LocalDate.of(2023, 7, 19));
        playlist.setSongs(new ArrayList<>());

        Song song1 = new Song("pesna1", 190, LocalDate.of(2020, 12, 20), GenreType.POP, artist);
        Song song2 = new Song("pesna2", 180, LocalDate.of(2010, 8, 11), GenreType.FOLK, artist);
        Song song3 = new Song("pesna3", 380, LocalDate.of(2019, 9, 13), GenreType.FOLK, artist);
        Song song4 = new Song("pesna4", 380, LocalDate.of(2001, 1, 12), GenreType.FOLK, artist);


        songRepository.saveAndFlush(song1);
        songRepository.saveAndFlush(song2);
        songRepository.saveAndFlush(song3);
        songRepository.saveAndFlush(song4);

        playlist.getSongs().add(song1);
        playlist.getSongs().add(song2);
        playlist.getSongs().add(song3);
        playlist.getSongs().add(song4);

        playlistRepository.saveAndFlush(playlist);


        Artist artist2 = new Artist("elena", "elena", LocalDate.of(1987, 4, 27), "macedonian", new ArrayList<>());
        artistRepository.saveAndFlush(artist2);

        playlist = new Playlist();
        playlist.setName("turbo folk");
        playlist.setStatus(StatusType.PUBLIC);
        playlist.setDateOfCreation(LocalDate.of(2023, 7, 19));
        playlist.setSongs(new ArrayList<>());

        Song song5 = new Song("pesna5", 190, LocalDate.of(2020, 12, 20), GenreType.FOLK, artist2);
        Song song6 = new Song("pesna6", 180, LocalDate.of(2010, 8, 11), GenreType.FOLK, artist2);


        songRepository.saveAndFlush(song5);
        songRepository.saveAndFlush(song6);

        playlist.getSongs().add(song1);
        playlist.getSongs().add(song2);

        playlistRepository.saveAndFlush(playlist);

    }

    @AfterEach
    public void cleanUpAll() {
        playlistRepository.deleteAll();
    }

    @Test
    @Transactional
    public void assertThatPlaylistMustExist() {
        Long playlistId = playlist.getId();
        Playlist maybePlaylist = playlistService.findById(playlistId);
        Assertions.assertNotNull(maybePlaylist);

        Assertions.assertEquals(maybePlaylist.getSongs().size(), playlist.getSongs().size());

        Assertions.assertThrows(PlaylistNotFoundException.class, () -> {
            playlistService.findById(99L);
        });

    }

    @Test
    @Transactional
    public void assertThatPlaylistExists() {
        List<Playlist> maybePlaylists = playlistService.listAll();
        Assertions.assertNotNull(maybePlaylists);
        maybePlaylists.stream().forEach(playlist1 -> {
            System.out.println(playlist1.getName());
        });
        Assertions.assertEquals(2, maybePlaylists.size());
    }

    @Test
    @Transactional
    public void assertFindPublicPlaylistsWithMax3SongsWorks() {
        List<Playlist> playlists = playlistService.findPlaylists();
        Assertions.assertNotNull(playlists);
        Assertions.assertEquals(1, playlists.size());
        Assertions.assertEquals("turbo folk", playlists.get(0).getName());
    }


    @Test
    @Transactional
    public void assertThatPlaylistDeleteMethodWorks() {
        Long playlistId = playlist.getId();
        playlistService.delete(playlistId);

        Assertions.assertThrows(PlaylistNotFoundException.class, () -> {
            playlistService.findById(playlistId);
        }, "PlaylistNotFoundException should be thrown when artist is not found");
    }
}

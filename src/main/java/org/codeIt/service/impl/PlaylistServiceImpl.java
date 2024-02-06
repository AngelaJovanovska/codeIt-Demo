package com.project.demo.service.impl;

import com.project.demo.model.Playlist;
import com.project.demo.model.Song;
import com.project.demo.model.StatusType;
import com.project.demo.model.exceptions.PlaylistAlreadyExistsException;
import com.project.demo.model.exceptions.PlaylistNotFoundException;
import com.project.demo.model.exceptions.SongNotFoundException;
import com.project.demo.repository.PlaylistRepository;
import com.project.demo.repository.SongRepository;
import com.project.demo.service.PlaylistService;
import com.project.demo.web.requests.Playlist.PlaylistCreateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(SongRepository songRepository, PlaylistRepository playlistRepository) {
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
    }


    @Override
    public List<Playlist> listAll() {
        return this.playlistRepository.findAll();
    }

    @Override
    public List<Playlist> findPlaylists() {
       return playlistRepository.getAllPublicPlaylistsWithMax3Songs();
    }
    @Override
    public Integer calculateDuration(Long id){
        return this.playlistRepository.calculateDurationOfAPlaylist(id);
    }


    @Override
    public Playlist findById(Long id) {
        return this.playlistRepository.findById(id).orElseThrow(PlaylistNotFoundException::new);
    }


    @Override
    public Playlist create(PlaylistCreateRequest playlistCreateRequest) {
        List<Song> songs = playlistCreateRequest.songs.stream()
                .map(songId -> this.songRepository.findById(songId).orElseThrow(SongNotFoundException::new)).toList();

        Optional<Playlist> existingPlaylist = Optional.ofNullable(playlistRepository.findPlaylistsByName(playlistCreateRequest.name));
        if (existingPlaylist.isPresent()) {
            throw new PlaylistAlreadyExistsException("A playlist with the name '" + playlistCreateRequest.name + "' already exists");
        }


        Playlist p = new Playlist(playlistCreateRequest.name, playlistCreateRequest.dateOfCreation, playlistCreateRequest.status,
                songs);

        Playlist playlist = this.playlistRepository.save(p);

        List<Song> songsToUpdate = songs.stream().map(song -> {

            List<Playlist> songPlaylists = song.getPlaylist();

            songPlaylists.add(p);
            return song;
        }).collect(Collectors.toList());
        this.songRepository.saveAll(songsToUpdate);

        return playlist;
    }

    @Override
    public Playlist update(Long id, String name, LocalDate dateOfCreation, StatusType status, List<Long> songsIds) {
        Playlist playlist = findById(id);
        playlist.setName(name);
        playlist.setStatus(status);
        playlist.setDateOfCreation(dateOfCreation);
        return this.playlistRepository.save(playlist);
    }

    @Override
    public Playlist delete(Long id) {
        Playlist playlist = findById(id);
        this.playlistRepository.delete(playlist);
        return playlist;
    }

    @Override
    public List<Playlist> getAllPlaylistsContainingSongs() {
            List<Playlist> playlists = this.playlistRepository.findAll();
            return this.playlistRepository.getAllPlaylistsContainingSongs();
    }

    @Override
    public Playlist findByName(String name) {
        return this.playlistRepository.findPlaylistsByName(name);
    }
}

package com.project.demo.service;

import com.project.demo.model.Playlist;
import com.project.demo.model.StatusType;
import com.project.demo.web.requests.Playlist.PlaylistCreateRequest;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PlaylistService {
    List<Playlist> listAll();

    List<Playlist> findPlaylists();
    Playlist findById(Long id);
    Integer calculateDuration(Long id);
    Playlist create(PlaylistCreateRequest playlistCreateRequest);
    Playlist update(Long id, String name, LocalDate dateOfCreation, StatusType status, List<Long> songsIds);
    Playlist delete(Long id);

    List<Playlist> getAllPlaylistsContainingSongs();

    Playlist findByName(String name);


}

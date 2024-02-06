package com.project.demo.web.responses.Playlist;

import com.project.demo.model.Playlist;
import com.project.demo.model.StatusType;
import com.project.demo.web.responses.Song.SongResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistResponse {
    public PlaylistResponse(Playlist playlist) {
        this.id = playlist.getId();
        this.name = playlist.getName();
        this.dateOfCreation = playlist.getDateOfCreation();
        this.status = playlist.getStatus();
        this.songs = playlist.getSongs().stream().map(SongResponse::new).collect(Collectors.toList());
    }

    public Long id;
    public String name;
    public LocalDate dateOfCreation;
    public StatusType status;
    public List<SongResponse> songs;
}

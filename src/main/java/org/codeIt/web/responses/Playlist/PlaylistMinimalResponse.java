package com.project.demo.web.responses.Playlist;

import com.project.demo.model.Artist;
import com.project.demo.model.Playlist;

import java.time.LocalDate;

public class PlaylistMinimalResponse {
    public String name;
    public LocalDate dateOfCreation;

    public PlaylistMinimalResponse(Playlist playlist) {
        this.name = playlist.getName();
        this.dateOfCreation = playlist.getDateOfCreation();
    }
}

package com.project.demo.web.requests.Playlist;

import com.project.demo.model.StatusType;

import java.time.LocalDate;
import java.util.List;

public class PlaylistCreateRequest {


    public String name;
    public LocalDate dateOfCreation;
    public StatusType status;
    public List<Long> songs;
}

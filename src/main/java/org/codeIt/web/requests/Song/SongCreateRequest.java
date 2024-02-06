package com.project.demo.web.requests.Song;

import com.project.demo.model.GenreType;

import java.time.LocalDate;

public class SongCreateRequest {


    public String title;

    public int duration;

    public LocalDate releaseDate;

    public GenreType genre;

    public Long artistId;
}

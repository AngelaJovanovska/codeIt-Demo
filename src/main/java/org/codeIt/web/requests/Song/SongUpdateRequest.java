package com.project.demo.web.requests.Song;

import com.project.demo.model.GenreType;

import java.time.LocalDate;

public class SongUpdateRequest {
    public Long id;

    public String title;

    public Integer duration;

    public LocalDate releaseDate;

    public GenreType genre;

    public Long artistId;
}

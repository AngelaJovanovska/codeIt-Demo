package com.project.demo.web.responses.Song;

import com.project.demo.model.GenreType;
import com.project.demo.model.Song;

import java.time.LocalDate;
import java.util.List;

public class SongResponse {
    public SongResponse(){}
    public SongResponse(Song song){
        this.id =song.getId();
        this.title =song.getTitle();
        this.duration =song.getDuration();
        this.genre = song.getGenre();
        this.releaseDate =song.getReleaseDate();
        this.artistId =song.getArtist().getId();

    }

    public Long id;

    public String title;

    public Integer duration;

    public LocalDate releaseDate;

    public GenreType genre;

    public Long artistId;
}

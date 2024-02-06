package com.project.demo.web.responses.Song;

import com.project.demo.model.Song;

import java.time.LocalDate;

public class SongMinimalResponse {

    public String title;
    public LocalDate releaseDate;

//    public Integer duration;
    public SongMinimalResponse(Song song) {
        this.title = song.getTitle();
        this.releaseDate = song.getReleaseDate();
//        this.duration = song.getDuration();
    }


}

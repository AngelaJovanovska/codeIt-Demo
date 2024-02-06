package com.project.demo.web.responses.Playlist;

public class PlaylistDurationResponse {
    public Integer duration;


    public PlaylistDurationResponse(Integer duration) {
        this.duration = duration/60;
    }

}

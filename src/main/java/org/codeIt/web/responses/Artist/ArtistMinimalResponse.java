package com.project.demo.web.responses.Artist;

import com.project.demo.model.Artist;

public class ArtistMinimalResponse {
    public String artisticName;
    public String realName;

//    public Integer duration;
//
    public ArtistMinimalResponse(Artist artist) {
        this.artisticName = artist.getArtisticName();
        this.realName = artist.getRealName();
//        this.duration = artist.getd
    }
}

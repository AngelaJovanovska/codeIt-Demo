package com.project.demo.web.responses.Artist;

import com.project.demo.model.Artist;

import com.project.demo.web.responses.Song.SongMinimalResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistResponse {
    public ArtistResponse(Artist artist) {
        this.id = artist.getId();
        this.realName = artist.getRealName();
        this.artisticName = artist.getArtisticName();
        this.dateOfBirth = artist.getDateOfBirth();
        this.nationality = artist.getNationality();
        this.songCollection = artist.getSongCollection().stream().map(SongMinimalResponse::new).collect(Collectors.toList());
    }

    public  Long id;
    public  String realName;
    public  String artisticName;
    public  LocalDate dateOfBirth;
    public  String nationality;
    public  List<SongMinimalResponse> songCollection;
}

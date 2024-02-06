package com.project.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Song {

//    Each song has a title, duration (minutes), release date, and belongs to a specific genre.
    public Song(){}

    public Song(String title, int duration, LocalDate releaseDate, GenreType genre
            , Artist artist)
    {
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.artist = artist;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "duration")
    private Integer duration;
    @Column(name="release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private GenreType genre;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToMany(mappedBy = "songs")
    private List<Playlist> playlist;


}

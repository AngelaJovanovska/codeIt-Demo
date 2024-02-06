package com.project.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Playlist {
//    Each playlist has a name, date of creation, status (is private or public) and a collection of the existing songs
    public  Playlist(){}

    public Playlist(String name, LocalDate dateOfCreation, StatusType status, List<Song> songs){
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.status = status;
        this.songs = songs;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_creation")
    private LocalDate dateOfCreation;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @ManyToMany
    @JoinTable(
            name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs;

}

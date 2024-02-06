package com.project.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Artist {

    //    Each artist has a name, artistic name, date of birth, nationality, and a collection of songs.
    public Artist() {
    }

    public Artist(String realName, String artisticName, LocalDate dateOfBirth, String nationality, List<Song> songCollection) {
        this.realName = realName;
        this.artisticName = artisticName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.songCollection = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "real_name")
    private String realName;
    @Column(name = "artistic_name")
    private String artisticName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "song_collection")
    @OneToMany(mappedBy = "artist")
    @JsonBackReference
    private List<Song> songCollection;
}

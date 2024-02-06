package com.project.demo.service;

import com.project.demo.model.Artist;
import com.project.demo.model.GenreType;

import java.time.LocalDate;
import java.util.List;

public interface ArtistService {

    List<Artist> filterByDateOfBirthAndNationality(LocalDate dateOfBirth, String nationality);
    Artist findByIdWithAllDetails(Long id);

    Artist findById(Long id);
    Artist findLongestDuration(Long id);
    List<Artist> findAll();
    Artist create(Artist artist);
    Artist delete(Long id);
}

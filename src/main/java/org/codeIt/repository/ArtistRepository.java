package com.project.demo.repository;

import com.project.demo.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByDateOfBirthBeforeAndNationality(LocalDate dateOfBirth, String nationality);
}

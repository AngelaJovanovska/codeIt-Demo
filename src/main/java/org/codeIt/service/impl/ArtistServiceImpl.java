package com.project.demo.service.impl;

import com.project.demo.model.Artist;
import com.project.demo.model.Song;
import com.project.demo.model.exceptions.ArtistNotFoundException;
import com.project.demo.repository.ArtistRepository;
import com.project.demo.repository.SongRepository;
import com.project.demo.service.ArtistService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }


    @Override
    public List<Artist> filterByDateOfBirthAndNationality(LocalDate dateOfBirth, String nationality) {
        List <Artist> artists = this.artistRepository.findAll();

        return artists.stream()
                .filter(artist -> artist.getNationality().equals(nationality) && artist.getDateOfBirth().isBefore(dateOfBirth))
                .collect(Collectors.toList());

    }

    @Override
    public Artist findByIdWithAllDetails(Long id) {

        Artist artist = this.artistRepository.findById(id).orElseThrow(ArtistNotFoundException::new);
        List<Song> songs = this.songRepository.findByArtist_IdOrderByTitle(id);
//        artist.setSongCollection(artist.getSongCollection().sort();

        artist.setSongCollection(songs);
        return artist;
    }

    @Override
    public Artist findById(Long id) {
        return artistRepository.findById(id).orElseThrow(ArtistNotFoundException::new);
    }

    @Override
    public Artist findLongestDuration(Long id){
        Artist artist = this.artistRepository.findById(id).orElseThrow(ArtistNotFoundException::new);
        List<Song> songs = this.songRepository.findByArtistId(id);
        Song longestDuration = songs.stream().max(Comparator.comparingInt(Song::getDuration)).orElse(null);
        if(longestDuration != null ){
            artist.setSongCollection(songs);
        }

        return artist;
    }

    @Override
    public List<Artist> findAll() {

        return artistRepository.findAll();
    }

    @Override
    public Artist create(Artist artist){
        return this.artistRepository.save(artist);
    }

    @Override
    public Artist delete(Long id) {
        Artist artist = findById(id);
        this.artistRepository.delete(artist);
        return  artist;
    }


}

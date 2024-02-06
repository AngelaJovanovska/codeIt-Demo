package com.project.demo.service.impl;

import com.project.demo.model.Artist;
import com.project.demo.model.Song;
import com.project.demo.model.exceptions.ArtistNotFoundException;
import com.project.demo.model.exceptions.SongNotFoundException;
import com.project.demo.repository.ArtistRepository;
import com.project.demo.repository.SongRepository;
import com.project.demo.service.SongService;
import com.project.demo.web.requests.Song.SongCreateRequest;
import com.project.demo.web.requests.Song.SongUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImp implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongServiceImp(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Song> listAll() {
        return songRepository.findAll();
    }

    @Override
    public Song findById(Long id) {
        return songRepository.findById(id).orElseThrow(SongNotFoundException::new);
    }

    @Override
    public Song create(SongCreateRequest songCreateRequest) {
       Artist artist = this.artistRepository.findById(songCreateRequest.artistId).orElseThrow(ArtistNotFoundException::new);
//        if(artist == null){
//            throw new InvalidArtistIdException();
//        }
       Song song = new Song(songCreateRequest.title,songCreateRequest.duration,songCreateRequest.releaseDate,songCreateRequest.genre,artist);
        return songRepository.save(song);
    }


    @Override
    public Song update(SongUpdateRequest songUpdateRequest){
        Artist artist = this.artistRepository.findById(songUpdateRequest.artistId).orElseThrow(ArtistNotFoundException::new);
        Song song = this.songRepository.findById(songUpdateRequest.id).orElseThrow(SongNotFoundException::new);
        song.setTitle(songUpdateRequest.title);
        song.setDuration(songUpdateRequest.duration);
        song.setGenre(songUpdateRequest.genre);
        song.setReleaseDate(songUpdateRequest.releaseDate);
        song.setArtist(artist);

        return songRepository.save(song);
    }

    @Override
    public Song delete(Long id) {
        Song song = findById(id);
        this.songRepository.delete(song);
        return song;
    }

    @Override
    public Song findLongestSongByArtistIdAndGenre(Long id, String genreType) {
        return this.songRepository.findLongestSongByArtistIdAndGenre(id,genreType);
    }

    @Override
    public List<Song> getSongsBetweenDuration() {
        return this.songRepository.getSongsBetweenDuration();
    }
}

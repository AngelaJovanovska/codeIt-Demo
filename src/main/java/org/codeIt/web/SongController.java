package com.project.demo.web;

import com.project.demo.model.Song;
import com.project.demo.model.exceptions.SongNotFoundException;
import com.project.demo.web.requests.Song.SongCreateRequest;
import com.project.demo.service.SongService;
import com.project.demo.web.requests.Song.SongUpdateRequest;
import com.project.demo.web.responses.Song.SongCreateResponse;
import com.project.demo.web.responses.Song.SongResponse;
import com.project.demo.web.responses.Song.SongUpdateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSong(@PathVariable Long id) {
        try {
            Song song = this.songService.findById(id);
            System.out.println(song.getTitle());
            SongResponse songResponse = new SongResponse(song);
            return ResponseEntity.ok(songResponse);
        } catch (SongNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/artist/{artist_id}/{genreType}")//F
    public ResponseEntity<SongResponse> getLongestSongByArtistAndGenre(@PathVariable Long artist_id, @PathVariable String genreType) {
        try {
            Song song = this.songService.findLongestSongByArtistIdAndGenre(artist_id, genreType);
            SongResponse songResponse = new SongResponse(song);
            return ResponseEntity.ok(songResponse);
        } catch (SongNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/betweenduration")//L
    public ResponseEntity<List<SongResponse>> getSongsBetweenDuration() {
        List<Song> songs = songService.getSongsBetweenDuration();
        List<SongResponse> songResponses = songs.stream().map(SongResponse::new).toList();
        return ResponseEntity.ok(songResponses);

    }

    @GetMapping
    public ResponseEntity<List<SongResponse>> findAllSongs() {
        List<Song> songs = songService.listAll();
        List<SongResponse> songResponses = songs.stream().map(SongResponse::new).collect(Collectors.toList());

        return ResponseEntity.ok(songResponses);

    }

    @PostMapping()//B
    public ResponseEntity<SongCreateResponse> saveSong(@RequestBody SongCreateRequest songCreateRequest) {
        //validate createReq
        try {
            Song song = songService.create(songCreateRequest);
            SongCreateResponse songCreateResponse = new SongCreateResponse(song);
            return ResponseEntity.ok(songCreateResponse);
        } catch (SongNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping()
    public ResponseEntity<SongUpdateResponse> updateSong(@RequestBody SongUpdateRequest songUpdateRequest) {
        Song song = songService.update(songUpdateRequest);
        SongUpdateResponse songUpdateResponse = new SongUpdateResponse(song);

        return ResponseEntity.ok(songUpdateResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SongResponse> deleteSong(@PathVariable Long id) {
        try {
            Song song = this.songService.findById(id);
            SongResponse songResponse = new SongResponse(song);
            this.songService.delete(id);
            return ResponseEntity.ok(songResponse);
        } catch (SongNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}

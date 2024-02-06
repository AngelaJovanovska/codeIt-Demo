package com.project.demo.web;

import com.project.demo.model.Playlist;
import com.project.demo.model.exceptions.PlaylistAlreadyExistsException;
import com.project.demo.model.exceptions.PlaylistNotFoundException;
import com.project.demo.service.PlaylistService;
import com.project.demo.web.requests.Playlist.PlaylistCreateRequest;
import com.project.demo.web.responses.Playlist.PlaylistCreateResponse;
import com.project.demo.web.responses.Playlist.PlaylistDurationResponse;
import com.project.demo.web.responses.Playlist.PlaylistMinimalResponse;
import com.project.demo.web.responses.Playlist.PlaylistResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> listAllPlaylists() {
        List<Playlist> playlists = playlistService.listAll();
        List<PlaylistResponse> playlistResponses = playlists.stream().map(PlaylistResponse::new).toList();
        return ResponseEntity.ok(playlistResponses);

    }

    @GetMapping("/public")//H
    public ResponseEntity<List<PlaylistMinimalResponse>> findPublicPlaylistsWithMax3Songs() {
        List<Playlist> playlists = playlistService.findPlaylists();
        List<PlaylistMinimalResponse> playlistResponses = playlists.stream().map(PlaylistMinimalResponse::new).toList();
        return ResponseEntity.ok(playlistResponses);
    }

    @GetMapping("/duration/{id}")//I
    public ResponseEntity<PlaylistDurationResponse> calculateDuration(@PathVariable Long id) {
        try {
            Playlist playlist = this.playlistService.findById(id);
            Integer durationTotal = playlistService.calculateDuration(id);
//        System.out.println(durationTotal.intValue());
            PlaylistDurationResponse p = new PlaylistDurationResponse(durationTotal);
            return ResponseEntity.ok(p);
        } catch (PlaylistNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/songs")//G
    public ResponseEntity<List<PlaylistResponse>> getAllPlaylistsContainingSongs() {
        List<Playlist> playlists = playlistService.getAllPlaylistsContainingSongs();
        List<PlaylistResponse> playlistResponses = playlists.stream().map(PlaylistResponse::new).toList();
        return ResponseEntity.ok(playlistResponses);

    }


    @PostMapping//C and J
    public ResponseEntity<PlaylistCreateResponse> create(@RequestBody PlaylistCreateRequest playlistCreateRequest) {
        try {
            Playlist playlist = this.playlistService.create(playlistCreateRequest);
            PlaylistCreateResponse playlistCreateResponse = new PlaylistCreateResponse(playlist);
            return ResponseEntity.ok(playlistCreateResponse);
        } catch (PlaylistAlreadyExistsException exception) {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/{id}")//K
    public ResponseEntity<PlaylistResponse> deletePlaylist(@PathVariable Long id) {
        try {
            Playlist playlist = this.playlistService.findById(id);
            PlaylistResponse playlistResponse = new PlaylistResponse(playlist);
            this.playlistService.delete(id);
            return ResponseEntity.ok(playlistResponse);
        } catch (PlaylistNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}

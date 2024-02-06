package com.project.demo.web;

import com.project.demo.model.Artist;
import com.project.demo.model.exceptions.ArtistNotFoundException;
import com.project.demo.service.ArtistService;
import com.project.demo.web.responses.Artist.ArtistMinimalResponse;
import com.project.demo.web.responses.Artist.ArtistResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/{id}")//E
    public ResponseEntity<ArtistResponse> getArtistWithAllDetails(@RequestBody @PathVariable Long id) {
        try {
            Artist artist = artistService.findByIdWithAllDetails(id);
            ArtistResponse artistResponse = new ArtistResponse(artist);
            return ResponseEntity.ok(artistResponse);
        } catch (ArtistNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get")
    public List<Artist> getAllArtists() {
        return this.artistService.findAll();
    }

    @GetMapping//D
    public ResponseEntity<List<ArtistMinimalResponse>> findAllArtistsFilterByDateOfBirthAndNationality() {
        try {
            List<Artist> artists = artistService.filterByDateOfBirthAndNationality(LocalDate.of(1999, 1, 1), "macedonian");
            List<ArtistMinimalResponse> artistResponses = artists.stream().map(ArtistMinimalResponse::new).toList();

            return ResponseEntity.ok(artistResponses);
        } catch (ArtistNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping//A
    public ResponseEntity<Artist> create(@RequestBody Artist artist) {
        Artist createdArtist = artistService.create(artist);
        return ResponseEntity.ok(createdArtist);
    }
 @DeleteMapping("/{id}")
    public ResponseEntity<ArtistResponse> delete(@PathVariable Long id) {
        try{
            Artist artist = artistService.findById(id);
            ArtistResponse artistResponse = new ArtistResponse(artist);
            this.artistService.delete(id);
            return ResponseEntity.ok(artistResponse);
        }catch (ArtistNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }


}

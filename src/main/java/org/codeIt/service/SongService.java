package com.project.demo.service;

import com.project.demo.model.Song;
import com.project.demo.web.requests.Song.SongCreateRequest;
import com.project.demo.web.requests.Song.SongUpdateRequest;

import java.util.List;

public interface SongService {
    List<Song> listAll();

    Song findById(Long id);

    Song create(SongCreateRequest songCreateRequest);

    Song update(SongUpdateRequest songUpdateRequest);

    Song delete(Long id);

    Song findLongestSongByArtistIdAndGenre(Long id, String genreType);

    List<Song> getSongsBetweenDuration();

}

package com.project.demo.repository;

import com.project.demo.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByArtist_IdOrderByTitle(Long id);

    List<Song> findByArtistId(Long id);

    @Query(value = """
        SELECT * from song s
        WHERE s.duration =
        (SELECT max(s.duration) as longest_duration FROM song s
        WHERE s.artist_id = :id AND s.genre = :genreType)
        limit 1
        
        """,nativeQuery = true)
    Song findLongestSongByArtistIdAndGenre(@Param("id") Long id, @Param("genreType") String genreType);


    @Query(value = """
        SELECT * from song s
        where s.duration >= 300 and s.duration <= 600
        order by s.duration DESC
        limit 3
        """, nativeQuery = true)
    List<Song> getSongsBetweenDuration();

}


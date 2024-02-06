package com.project.demo.repository;

import com.project.demo.model.Playlist;
import com.project.demo.model.Song;
import com.project.demo.model.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query(value = """
            SELECT p.id, p.status, p.name, p.date_of_creation from playlist p INNER JOIN
            (SELECT playlist.id, count(playlist_songs.song_id) as n_songs from playlist
                left join playlist_songs ON playlist.id = playlist_songs.playlist_id
                where playlist.status = 'PUBLIC'
                GROUP BY playlist.id) as x
                on p.id = x.id
            WHERE x.n_songs <= 3""", nativeQuery = true)
    List<Playlist> getAllPublicPlaylistsWithMax3Songs();


    @Query(value = """
            SELECT SUM(s.duration) AS total_duration
            FROM playlist p
            JOIN playlist_songs ps ON p.id = ps.playlist_id
            JOIN song s ON ps.song_id = s.id
            WHERE p.id = :id
            """,nativeQuery = true)
    Integer calculateDurationOfAPlaylist(@Param("id") Long id);


    @Query(value= """
    select distinct(p.id), p.name, p.date_of_creation, p.status from playlist_songs ps
    INNER JOIN playlist p
    on ps.playlist_id = p.id
    WHERE ps.song_id IN
    (SELECT s.id from song s
    INNER JOIN artist a ON s.artist_id = a.id
     order by a.artistic_name, a.date_of_birth asc
    )
    """,nativeQuery = true)
    List<Playlist> getAllPlaylistsContainingSongs();



    Playlist findPlaylistsByName(String name);
}

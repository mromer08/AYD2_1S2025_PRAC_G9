package com.usac.ayd2.musicplayer.models.music;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "playlist_songs", uniqueConstraints = @UniqueConstraint(columnNames = {"playlist_id", "song_id"}))
public class PlaylistSong {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;
    
}

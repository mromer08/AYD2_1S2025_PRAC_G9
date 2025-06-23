package com.usac.ayd2.musicplayer.patterns.facade;

import java.util.List;

import com.usac.ayd2.musicplayer.dto.music.NewPlaylistDTO;
import com.usac.ayd2.musicplayer.dto.music.PlaylistDTO;
import com.usac.ayd2.musicplayer.dto.music.SongDTO;

public interface MusicPlayerFacade {

    List<SongDTO> getAllSongs();
    List<PlaylistDTO> getPlaylistsByUser(Long userId);
    List<SongDTO> getSongsInPlaylist(Long playlistId);
    void addSongToPlaylist(Long playlistId, Long songId);
    void removeSongFromPlaylist(Long playlistId, Long songId);
    void savePlaylist(NewPlaylistDTO newPlaylistDTO);
    void renamePlaylist(Long playlistId, String newName);
    void deletePlaylist(Long playlistId);

}

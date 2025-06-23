package com.usac.ayd2.musicplayer.patterns.facade;

import java.util.List;

import com.usac.ayd2.musicplayer.dto.music.NewPlaylistDTO;
import com.usac.ayd2.musicplayer.dto.music.PlaylistDTO;
import com.usac.ayd2.musicplayer.dto.music.SongDTO;
import com.usac.ayd2.musicplayer.services.music.PlaylistServiceImpl;
import com.usac.ayd2.musicplayer.services.music.PlaylistSongServiceImpl;
import com.usac.ayd2.musicplayer.services.music.SongServiceImpl;

public class MusicPlayerFacadeImpl implements MusicPlayerFacade {

    private final SongServiceImpl songService = SongServiceImpl.getInstance();
    private final PlaylistServiceImpl playlistService = PlaylistServiceImpl.getInstance();
    private final PlaylistSongServiceImpl playlistSongService = PlaylistSongServiceImpl.getInstance();

    @Override
    public List<SongDTO> getAllSongs() {
        return songService.getAllSongs();
    }

    @Override
    public List<PlaylistDTO> getPlaylistsByUser(Long userId) {
        return playlistService.getPlaylistsByUser(userId);
    }

    @Override
    public List<SongDTO> getSongsInPlaylist(Long playlistId) {
        return playlistSongService.getSongsInPlaylist(playlistId);
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId) {
        playlistSongService.addSongToPlaylist(playlistId, songId);
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        playlistSongService.removeSongFromPlaylist(playlistId, songId);
    }

    @Override
    public void savePlaylist(NewPlaylistDTO newPlaylistDTO) {
        playlistService.savePlaylist(newPlaylistDTO);
    }

    @Override
    public void renamePlaylist(Long playlistId, String newName) {
        playlistService.renamePlaylist(playlistId, newName);
    }

    @Override
    public void deletePlaylist(Long playlistId) {
        playlistService.deletePlaylist(playlistId);
    }
    
}

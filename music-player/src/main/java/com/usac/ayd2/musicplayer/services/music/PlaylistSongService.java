package com.usac.ayd2.musicplayer.services.music;

import com.usac.ayd2.musicplayer.dto.music.SongDTO;
import java.util.List;

public interface PlaylistSongService {
    
    List<SongDTO> getSongsInPlaylist(Long playlistId);
    void addSongToPlaylist(Long playlistId, Long songId);
    void removeSongFromPlaylist(Long playlistId, Long songId);
    
}

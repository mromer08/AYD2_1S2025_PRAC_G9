package com.usac.ayd2.musicplayer.services.music;

import com.usac.ayd2.musicplayer.dto.music.NewPlaylistDTO;
import com.usac.ayd2.musicplayer.dto.music.PlaylistDTO;
import java.util.List;

/**
 *
 * @author adolfo-son
 */
public interface PlaylistService {

    void savePlaylist(NewPlaylistDTO newPlayListDTO);
    List<PlaylistDTO> getPlaylistsByUser(Long userId);
    PlaylistDTO getPlaylistById(Long id);
    void renamePlaylist(Long id, String name);
    void deletePlaylist(Long id); 
    
}

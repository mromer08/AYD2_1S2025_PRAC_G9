package com.usac.ayd2.musicplayer.services.music;

import com.usac.ayd2.musicplayer.dto.music.NewSongDTO;
import com.usac.ayd2.musicplayer.dto.music.SongDTO;
import java.util.List;

/**
 *
 * @author adolfo-son
 */
public interface SongService {

    void saveSong(NewSongDTO newSongDTO);
    SongDTO getSongById(Long id);
    List<SongDTO> getAllSongs();

} 

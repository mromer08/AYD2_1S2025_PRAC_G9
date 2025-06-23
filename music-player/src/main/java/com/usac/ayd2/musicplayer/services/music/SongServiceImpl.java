package com.usac.ayd2.musicplayer.services.music;

import java.util.List;

import com.usac.ayd2.musicplayer.dao.music.SongDAO;
import com.usac.ayd2.musicplayer.dto.music.NewSongDTO;
import com.usac.ayd2.musicplayer.dto.music.SongDTO;
import com.usac.ayd2.musicplayer.mappers.music.SongMapper;
import com.usac.ayd2.musicplayer.models.music.Song;
import com.usac.ayd2.musicplayer.utils.AlertUtil;

import javafx.scene.control.Alert.AlertType;

public class SongServiceImpl implements SongService {

    private static SongServiceImpl instance;

    private final SongDAO songDAO = new SongDAO();
    private final SongMapper songMapper = SongMapper.INSTANCE;

    private SongServiceImpl() { }

    public static SongServiceImpl getInstance() {
        if (instance == null) {
            instance = new SongServiceImpl();
        }
        return instance;
    }

    @Override
    public void saveSong(NewSongDTO newSongDTO) {
        final Song song = songMapper.toSong(newSongDTO);
        songDAO.saveSong(song);
    }

    @Override
    public SongDTO getSongById(Long id) {
        final Song song = songDAO.findById(id);
        if (song == null) {
            AlertUtil.showAlert(AlertType.ERROR, "No existe la cancion con id: " + id.toString());
            return null;
        }
        return songMapper.toSongDTO(song);
    }

    @Override
    public List<SongDTO> getAllSongs() {
        final List<SongDTO> songs = songDAO.getAllSongs()
                .stream()
                .map(songMapper::toSongDTO)
                .toList();
        return songs;
    }
    
}

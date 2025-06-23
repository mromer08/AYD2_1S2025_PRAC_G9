package com.usac.ayd2.musicplayer.services.music;

import java.util.List;

import com.usac.ayd2.musicplayer.dao.music.PlaylistDAO;
import com.usac.ayd2.musicplayer.dao.user.UserDAO;
import com.usac.ayd2.musicplayer.dto.music.NewPlaylistDTO;
import com.usac.ayd2.musicplayer.dto.music.PlaylistDTO;
import com.usac.ayd2.musicplayer.mappers.music.PlaylistMapper;
import com.usac.ayd2.musicplayer.models.music.Playlist;
import com.usac.ayd2.musicplayer.models.user.User;
import com.usac.ayd2.musicplayer.utils.AlertUtil;

import javafx.scene.control.Alert.AlertType;

public class PlaylistServiceImpl implements PlaylistService {

    private static PlaylistServiceImpl instance;

    private final PlaylistDAO playlistDAO = new PlaylistDAO();
    private final UserDAO userDAO = new UserDAO();
    private final PlaylistMapper playlistMapper = PlaylistMapper.INSTANCE; 

    private PlaylistServiceImpl() { }

    public static PlaylistServiceImpl getInstance() {
        if (instance == null) {
            instance = new PlaylistServiceImpl();
        }
        return instance;
    }

    @Override
    public void savePlaylist(NewPlaylistDTO newPlayListDTO) {
        final Playlist playlist = playlistMapper.toPlaylist(newPlayListDTO);
        final User user = userDAO.findByUsername(newPlayListDTO.username());
        playlist.setUser(user);
        playlistDAO.savePlaylist(playlist);
    }

    @Override
    public List<PlaylistDTO> getPlaylistsByUser(Long userId) {
        final List<PlaylistDTO> playlists = playlistDAO.getPlaylistsByUser(userId)
                .stream()
                .map(playlistMapper::toPlaylistDTO)
                .toList();
        return playlists;
    }

    @Override
    public PlaylistDTO getPlaylistById(Long id) {
        final Playlist playlist = playlistDAO.findById(id);
        if (playlist == null) {
            AlertUtil.showAlert(AlertType.ERROR, "No existe la playlist con el id: " + id.toString());
            return null;
        }
        return playlistMapper.toPlaylistDTO(playlist);
    }

    @Override
    public void renamePlaylist(Long id, String name) {
        final Playlist playlist = playlistDAO.findById(id);
        if (playlist == null) {
            AlertUtil.showAlert(AlertType.ERROR, "No existe la playlist con el id: " + id.toString());
            return;
        }
        playlist.setName(name);
        playlistDAO.updatePlaylist(playlist);
    }

    @Override
    public void deletePlaylist(Long id) {
        final Playlist playlist = playlistDAO.findById(id);
        if (playlist == null) {
            AlertUtil.showAlert(AlertType.ERROR, "No existe la playlist con el id: " + id.toString());
            return;
        }
        playlistDAO.deletePlaylist(playlist);
    }

    
}
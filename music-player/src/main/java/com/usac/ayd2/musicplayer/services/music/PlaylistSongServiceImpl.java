package com.usac.ayd2.musicplayer.services.music;

import java.util.List;

import com.usac.ayd2.musicplayer.dao.music.PlaylistDAO;
import com.usac.ayd2.musicplayer.dao.music.PlaylistSongDAO;
import com.usac.ayd2.musicplayer.dao.music.SongDAO;
import com.usac.ayd2.musicplayer.dto.music.SongDTO;
import com.usac.ayd2.musicplayer.mappers.music.SongMapper;
import com.usac.ayd2.musicplayer.models.music.Playlist;
import com.usac.ayd2.musicplayer.models.music.Song;

public class PlaylistSongServiceImpl implements PlaylistSongService {

    private static PlaylistSongServiceImpl instance;

    private final PlaylistSongDAO playlistSongDAO = new PlaylistSongDAO();
    private final PlaylistDAO playlistDAO = new PlaylistDAO();
    private final SongDAO songDAO = new SongDAO();
    private SongMapper songMapper = SongMapper.INSTANCE;

    private PlaylistSongServiceImpl() { }

    public static PlaylistSongServiceImpl getInstance() {
        if (instance == null) {
            instance = new PlaylistSongServiceImpl();
        }
        return instance;
    }

    @Override
    public List<SongDTO> getSongsInPlaylist(Long playlistId) {
        final List<SongDTO> songs = playlistSongDAO.findByPlaylistId(playlistId)
                .stream()
                .map(playlistSong -> songMapper.toSongDTO(playlistSong.getSong()))
                .toList();
        return songs;
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId) {
        if (!playlistSongDAO.exists(playlistId, songId)) {
            Playlist playlist = playlistDAO.findById(playlistId);
            Song song = songDAO.findById(songId);
            if (playlist != null && song != null) {
                playlistSongDAO.addSongToPlaylist(playlist, song);
            }
        }
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        playlistSongDAO.removeSongFromPlaylist(playlistId, songId);
    }

}

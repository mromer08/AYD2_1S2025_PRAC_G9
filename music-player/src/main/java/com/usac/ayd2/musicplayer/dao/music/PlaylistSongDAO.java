
package com.usac.ayd2.musicplayer.dao.music;

import com.usac.ayd2.musicplayer.models.music.Playlist;
import com.usac.ayd2.musicplayer.models.music.PlaylistSong;
import com.usac.ayd2.musicplayer.models.music.Song;
import com.usac.ayd2.musicplayer.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PlaylistSongDAO {

    public List<PlaylistSong> findByPlaylistId(Long playlistId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<PlaylistSong> query = session.createQuery(
                    "FROM PlaylistSong ps WHERE ps.playlist.id = :playlistId", PlaylistSong.class);
            query.setParameter("playlistId", playlistId);
            return query.list();
        }
    }

    public void addSongToPlaylist(Playlist playlist, Song song) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            PlaylistSong ps = new PlaylistSong();
            ps.setPlaylist(playlist);
            ps.setSong(song);

            session.persist(ps);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }

    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query<PlaylistSong> query = session.createQuery(
                    "FROM PlaylistSong ps WHERE ps.playlist.id = :playlistId AND ps.song.id = :songId",
                    PlaylistSong.class);
            query.setParameter("playlistId", playlistId);
            query.setParameter("songId", songId);
            PlaylistSong result = query.uniqueResult();

            if (result != null) {
                session.remove(result);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }

    public boolean exists(Long playlistId, Long songId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(ps) FROM PlaylistSong ps WHERE ps.playlist.id = :playlistId AND ps.song.id = :songId",
                    Long.class);
            query.setParameter("playlistId", playlistId);
            query.setParameter("songId", songId);
            return query.uniqueResult() > 0;
        }
    }

}

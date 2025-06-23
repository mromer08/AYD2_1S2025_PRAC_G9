package com.usac.ayd2.musicplayer.dao.music;

import com.usac.ayd2.musicplayer.models.music.Playlist;
import com.usac.ayd2.musicplayer.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 *
 * @author
 */
public class PlaylistDAO {

    public void savePlaylist(Playlist playlist) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(playlist);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Playlist> getAllPlaylists() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Playlist", Playlist.class).list();
        }
    }

    public Playlist findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Playlist.class, id);
        }
    }

    public List<Playlist> getPlaylistsByUser(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Playlist WHERE user.id = :userId", Playlist.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    public void updatePlaylist(Playlist playlist) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(playlist);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deletePlaylist(Playlist playlist) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(playlist);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

}

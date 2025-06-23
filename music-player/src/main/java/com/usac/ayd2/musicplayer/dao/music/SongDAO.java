package com.usac.ayd2.musicplayer.dao.music;

import com.usac.ayd2.musicplayer.models.music.Song;
import com.usac.ayd2.musicplayer.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author adolfo-son
 */
public class SongDAO {
    
    public void saveSong(Song song) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(song);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Song> getAllSongs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Song", Song.class).list();
        }
    }

    public Song findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Song.class, id);
        }
    }
    
}

package com.jcg.hibernate.maven;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class LocalDAO {
	
//	static Session session;
	static SessionFactory sessionFactory;
	
	public LocalDAO() {
		try {
			sessionFactory = new Configuration().configure("localDAOhibernate.cfg.xml").buildSessionFactory();
			
		} catch (Exception e) {
			System.out.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
			System.exit(-1);
		}
//		session = sessionFactory.openSession();
	}
	
	public boolean createArtist(Artist artist) {
		
		Transaction transaction = null;
		
		try(Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			System.out.println(" onnistui1?");
			session.saveOrUpdate(artist);
			System.out.println(" onnistui2?");
			transaction.commit();
			System.out.println(" onnistui3?");
			return true;
		} catch(Exception e) {
			if(transaction != null) {
				System.out.println("Transaction not null");
				transaction.rollback();
				throw e;
			}
		}
		
		return true;
	}
	
	public Artist readArist(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Artist artist = (Artist)session.get(Artist.class, id);
		
		System.out.println("Found = " + artist.getArtistName());
		session.getTransaction().commit();
		session.close();
		return artist;
	}
	
	public boolean addAlbum(Album album)  {
		
		Transaction transaction = null;
		
		try(Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			System.out.println(" onnistui1?");
			session.saveOrUpdate(album);
			System.out.println(" onnistui2?");
			transaction.commit();
			System.out.println(" onnistui3?");
			return true;
		} catch(Exception e) {
			if(transaction != null) 
				System.out.println("Exception not null");
				transaction.rollback();
			throw e;
		}
	
	}
	
	public void finalize() {
		try {
			if(sessionFactory != null)
				sessionFactory.close();
		}catch(Exception e) {
			System.err.println("Session factory couldn't be closed: "+e.getMessage());
		}
	}

}

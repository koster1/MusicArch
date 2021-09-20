package com.jcg.hibernate.maven;

import java.util.List;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DAO {
	static Session session;
	static SessionFactory sessionFactory;
	
	private static SessionFactory buildSessionFactory() {
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		
		ServiceRegistry serviceReg = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		
		sessionFactory = config.buildSessionFactory(serviceReg);
		return sessionFactory;
	}
	
	public DAO(){
		
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
			}catch(Exception e){		
				System.err.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
				System.exit(-1);
			}	
		
//		session = buildSessionFactory().openSession();
//		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}
	
	public void finalize() {
		try {
			if(sessionFactory != null)
				sessionFactory.close();
		}catch(Exception e) {
			System.err.println("Session factory couldn't be closed: "+e.getMessage());
		}
	}
	
	public boolean createGenre(Genre genre) {
		System.out.println("We got this - >"+genre.getGenreID());
		
		Transaction transAct = null;
		
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			session.saveOrUpdate(genre);
			
			transAct.commit();
			return true;
		}catch(Exception e) {
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	
	public Genre readGenre(String tunnus) {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Genre genre = (Genre) session.get(Genre.class, tunnus);

			transAct.commit();

			return genre;

		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}

	}
	
	public Genre[] readGenret() {
		
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Genre> result = (List<Genre>) session.createQuery("from Genre").list();
			
			transAct.commit();
			Genre[] array = new Genre[result.size()];
			return (Genre[]) result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	/*THIS WAS IN THE OLD CONSTRUCTOR
	 * try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
			}catch(Exception e){		
				System.err.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
				System.exit(-1);
			}	
	 */
	
	

}

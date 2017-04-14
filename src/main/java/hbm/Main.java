package hbm;

import org.hibernate.Session;

import hbm.model.User;

public class Main {
	public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
 
        session.beginTransaction();
        User user = new User();
 
        user.setUsername("Zaher");
 
        session.save(user);
        session.getTransaction().commit();
    }
}

package gruppo01.clients;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import gruppo01.data.Operatore;
import gruppo01.data.Tour;

public class OperatoreLogin {
	
	public static boolean login (int id, String password) {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	        EntityManager em = emf.createEntityManager();
	        em.getTransaction().begin();
	        TypedQuery<Operatore> query= em.createQuery("SELECT o FROM Operatore o WHERE o.idOperatore = :id  and o.passwordOperatore = :password",Operatore.class);
	        query.setParameter("id", id);
	        query.setParameter("password",password);
	        try {
	        	Operatore operatore= query.getSingleResult();
	        }catch(Exception ex){
	        	return false;
	        }
	        return true;
	        	        
	}
}

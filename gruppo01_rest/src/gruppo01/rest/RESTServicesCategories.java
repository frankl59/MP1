package gruppo01.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import gruppo01.data.*;

@Path("/categories")
@Consumes
public class RESTServicesCategories {	

	@GET
	@Path("/")
	@Produces("application/json")
	public Response visualizzaTours() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<TourCategoria> query = em.createQuery("SELECT c FROM TourCategoria c",TourCategoria.class);    
		List<TourCategoria> categories = query.getResultList();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET")
				.entity(categories)
				.build();
	}
	
	@GET //to delete 
	@Path("/{tour_category}")
	@Produces("application/json")
	public Response visualizzaToursCategoria(
			@PathParam("tour_category") int tour_category) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TourCategoria cat=em.find(TourCategoria.class, tour_category);
		TypedQuery<Tour> query = em.createQuery("SELECT t FROM Tour t WHERE t.tourCategoria = :tourCategoria",Tour.class);    
		query.setParameter("tourCategoria",cat);
		List<Tour> categories = query.getResultList();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET")
				.entity(categories)
				.build();
	}
}

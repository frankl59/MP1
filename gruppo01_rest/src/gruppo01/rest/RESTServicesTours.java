package gruppo01.rest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import gruppo01.data.*;

@Path("/tours")
@Consumes
public class RESTServicesTours {	

	@GET
	@Path("/")
	@Produces("application/json")
	public Response visualizzaTours() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Tour> query = em.createQuery("SELECT t FROM Tour t",Tour.class);    
		List<Tour> tours = query.getResultList();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET")
				.entity(tours)
				.build();
	}
	
	@GET
	@Path("{tour_category}")
	@Produces("application/json")
	public Response visualizzaToursPerCategoria(
			@PathParam("tour_category") int tour_category) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TourCategoria categoria = em.find(TourCategoria.class, tour_category);
		TypedQuery<Tour> query = em.createQuery("SELECT t FROM Tour t where t.tourCategoria  = :categoria",Tour.class);  
		query.setParameter("categoria", categoria);  
		List<Tour> tours = query.getResultList();    
		em.getTransaction().commit();
		em.close();
		emf.close();
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET")
				.entity(tours)
				.build();
	}
	
	@GET
	  @Path("{tour_category}/{id_tour}")
	  @Produces("application/json")
	  public Response visualizzaToursDellaCategoria(
	      @PathParam("tour_category") int tour_category,
	      @PathParam("id_tour") int id_tour) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    TourCategoria categoria = em.find(TourCategoria.class, tour_category);
	    Tour tour=em.find(Tour.class,id_tour);
	    TypedQuery<Tour> query = em.createQuery("SELECT t FROM Tour t where t.tourCategoria  = :categoria and t.idTour  = :tour",Tour.class);  
	    query.setParameter("categoria", categoria);  
	    query.setParameter("tour", id_tour);  
	    TypedQuery<Partecipazioni> query2 = em.createQuery("SELECT p FROM Partecipazioni p where p.tour = :tour",Partecipazioni.class);
	    query2.setParameter("tour", tour);
	    List<Partecipazioni> partecipazioni=query2.getResultList();
	    int numero_partecipanti = partecipazioni.size();
	    Tour tourResult = query.getSingleResult();
	    JSONObject response=new JSONObject();
	    JSONObject category = new JSONObject();
	    try {
	      response.put("idTour", tourResult.getIdTour());
	      response.put("nomeTour", tourResult.getNomeTour());
	      response.put("imgTour", tourResult.getImgTour());
	      response.put("dataTour",tourResult.getDataTour().toGMTString());
	      response.put("durataMinTour", tourResult.getDurataMinTour());
	      response.put("costoTour", tourResult.getCostoTour());
	      response.put("numeroMassimoPartecipantiTour",tourResult.getNumeroMassimoPartecipantiTour());
	      response.put("descrizioneTour", tourResult.getDescrizioneTour());
	      response.put("numeroPartecipantiTour",numero_partecipanti);
	      category.put("descrizioneCategoriaTour", 1);
	      category.put("idCategoriaTour", 1);	  
	      response.put("tourCategoria",category);
	    } catch (JSONException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    
	    em.getTransaction().commit();
	    em.close();
	    emf.close();
	    return Response.ok()
	        .header("Access-Control-Allow-Origin", "*")
	        .header("Access-Control-Allow-Methods", "GET")
	        .entity(response.toString())
	        .build();
	  }
	
		
}

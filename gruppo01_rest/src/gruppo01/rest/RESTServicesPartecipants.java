package gruppo01.rest;


import java.util.Date;
import java.util.List;

import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.PathParam;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import gruppo01.data.*;

@Path("/partecipants")
@Consumes
public class RESTServicesPartecipants {
	
	@GET
	@Path("/")
	@Produces("application/json")
	public Response visualizzaPartecipanti() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Partecipante> query = em.createQuery("SELECT p FROM Partecipante p",Partecipante.class);    
		List<Partecipante> tours = query.getResultList();
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
	  @Path("/{user_partecipant}")
	  @Produces("application/json")
	  public Response visualizzaPartecipante(
	      @PathParam("user_partecipant") String user_partecipant) {
	    
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    TypedQuery<Partecipante> query = em.createQuery("SELECT p FROM Partecipante p WHERE p.usernamePartecipante = :username",Partecipante.class);
	    query.setParameter("username", user_partecipant);
	    List<Partecipante> partecipant=query.getResultList();
	    em.getTransaction().commit();
	    em.close();
	    emf.close();
	    if(partecipant!=null)
	      return Response.ok()
	        .header("Access-Control-Allow-Origin", "*")
	        .header("Access-Control-Allow-Methods", "GET")
	        .entity(partecipant)
	        .build();
	    else
	      return Response.status(404)
	          .header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").entity(partecipant).build();
	  }

	
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response registraPartecipante(
			final JsonObject partecipant_json) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Partecipante partecipante=new Partecipante();
		 if (partecipant_json.getString("name").length() != 0) {
			 partecipante.setNomePartecipante(partecipant_json.getString("name"));
	        }
	        if (partecipant_json.getString("email").length() != 0) {
	        	partecipante.setEmailPartecipante(partecipant_json.getString("email"));
	        }	       
	        if (partecipant_json.getString("surname").length() != 0) {
	        	partecipante.setCognomePartecipante(partecipant_json.getString("surname"));
	        }
	        if (partecipant_json.getString("password").length() != 0) {
	        	partecipante.setPasswordPartecipante(partecipant_json.getString("password"));
	        }
	        if (partecipant_json.getString("username").length() != 0) {
	        	partecipante.setUsernamePartecipante(partecipant_json.getString("username"));
	        }         
	        em.persist(partecipante);
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
	        System.out.println(partecipante.getUsernamePartecipante());
	        return Response.ok()
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "POST")
					.entity(partecipante)
					.build();
		
	}
	
	@POST
	@Path("/{user_partecipant}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response loginPartecipante(
			@PathParam("user_partecipant") String user_partecipant,
			final JsonObject partecipant_json) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Partecipante partecipant= em.find(Partecipante.class, user_partecipant);
		if(partecipant != null) {
			String password= partecipant.getPasswordPartecipante();
			System.out.println(password);
			String jsonpassword=partecipant_json.getString("password");		
			em.getTransaction().commit();
			em.close();
			emf.close();
			if(password.equals(jsonpassword))
				return Response.status(200).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "POST").build();
			else
				return Response.status(404).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "POST").build();	
		}else {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "POST").build();	
		}
		
	}
	
	@PUT
	@Path("/{user_partecipant}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response modificaPartecipante(
			@PathParam("user_partecipant") String user_partecipant,
			final JsonObject partecipant_json) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Partecipante partecipant=em.find(Partecipante.class, user_partecipant);
		if (partecipant!=null) {
			 if (partecipant_json.getString("name").length() != 0) {
				 partecipant.setNomePartecipante(partecipant_json.getString("name"));
		        }
		        if (partecipant_json.getString("email").length() != 0) {
		        	partecipant.setEmailPartecipante(partecipant_json.getString("email"));
		        }	       
		        if (partecipant_json.getString("surname").length() != 0) {
		        	partecipant.setCognomePartecipante(partecipant_json.getString("surname"));
		        }
		        if (partecipant_json.getString("password").length() != 0) {
		        	partecipant.setPasswordPartecipante(partecipant_json.getString("password"));
		        }
	        
		}
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
	        
	        return Response.ok()
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "PUT")
					.entity(partecipant)
					.build();
		
	}
	
	@DELETE
	  @Path("/{user_partecipant}")
	  @Produces("application/json")
	  public Response eliminaPartecipante(
	      @PathParam("user_partecipant") String user_partecipant) {
	    boolean delete=false;
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    Partecipante partecipant=em.find(Partecipante.class, user_partecipant);
	    if(partecipant!=null) {
	      em.remove(partecipant);
	      delete=true;
	      System.out.println("DELETE");
	    }
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
	        if(delete)
	          return Response.ok()
	        .header("Access-Control-Allow-Origin", "*")
	        .header("Access-Control-Allow-Methods", "DELETE")
	        .entity(partecipant)
	        .build();
	        else
	          return Response.status(404)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "DELETE")
	            .entity(partecipant)
	            .build();
	            
	  }
	@POST //richiede partecipazione ad un tour
	  @Path("/{user_partecipant}/tours/{id_tour}")
	  @Produces("application/json")
	  @Consumes("application/json")
	  public Response richiediPartecipazione(
	      @PathParam("user_partecipant") String user_partecipant,
	      @PathParam("id_tour") int id_tour) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    PartecipazioniPK key = new PartecipazioniPK();
	    key.setIdTour(id_tour);
	    key.setUsernamePartecipante(user_partecipant);
	    Tour tour=em.find(Tour.class,id_tour);
	    Partecipante partecipante =em.find(Partecipante.class,user_partecipant);
	    Partecipazioni partecipazione = new Partecipazioni();
	    partecipazione.setId(key);
	    partecipazione.setPartecipante(partecipante);
	    partecipazione.setTour(tour);
	    TourCategoria tourCategoria=em.find(TourCategoria.class, tour.getTourCategoria().getIdCategoriaTour());
	    partecipazione.setTourCategoria(tourCategoria);
	    partecipazione.setIdOperatore(tour.getOperatore().getIdOperatore());
	    em.persist(partecipazione);
	    em.getTransaction().commit();
	    em.close();
	    emf.close();
	    
	    return  Response.ok()
	        .header("Access-Control-Allow-Origin", "*")
	        .header("Access-Control-Allow-Methods", "POST")
	        .entity("")
	        .build();
	  }
	
	@DELETE //richiede partecipazione ad un tour
	  @Path("/{user_partecipant}/tours/{id_tour}")
	  @Produces("application/json")
	  @Consumes("application/json")
	  public Response cancellaPartecipazione(
	      @PathParam("user_partecipant") String user_partecipant,
	      @PathParam("id_tour") int id_tour) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();

	    PartecipazioniPK key = new PartecipazioniPK();
	    key.setIdTour(id_tour);
	    key.setUsernamePartecipante(user_partecipant);
	    Partecipazioni partecipazione = em.find(Partecipazioni.class, key);
	    
	    em.remove(partecipazione);
	    
	    em.getTransaction().commit();
	    em.close();
	    emf.close();
	    
	    if(partecipazione!=null)
		      return Response.ok()
		        .header("Access-Control-Allow-Origin", "*")
		        .header("Access-Control-Allow-Methods", "DELETE")
		        .build();
		    else
		      return Response.status(404)
		          .header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "DELETE").build();
	  }
	
	@GET
	@Path("/{user_partecipant}/tours/{id_tour}")
	  @Produces("application/json")
	  public Response visualizzaPartecipazione(
		      @PathParam("user_partecipant") String user_partecipant,
		      @PathParam("id_tour") int id_tour) {
	    
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    
	    PartecipazioniPK key = new PartecipazioniPK();
	    key.setIdTour(id_tour);
	    key.setUsernamePartecipante(user_partecipant);
	    Partecipazioni partecipazioni = em.find(Partecipazioni.class, key);
	    em.getTransaction().commit();
	    em.close();
	    emf.close();
	    if(partecipazioni!=null)
	    	
	      return Response.ok()
	        .header("Access-Control-Allow-Origin", "*")
	        .header("Access-Control-Allow-Methods", "GET")
	        .build();
	    else
	      return Response.status(404)
	          .header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").build();
	  }
	
	@GET
	  @Path("/{user_partecipant}/tours/")
	    @Produces("application/json")
	    public Response visualizzaPartecipazioni(
	          @PathParam("user_partecipant") String user_partecipant) {
	    Date date = new Date();
	    //System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
	      EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	      EntityManager em = emf.createEntityManager();
	      em.getTransaction().begin();
	      Partecipante partecipante=em.find(Partecipante.class,user_partecipant);
	      TypedQuery<Tour> query = em.createQuery("SELECT p.tour FROM Partecipazioni p WHERE p.partecipante = :partecipante",Tour.class);
	      query.setParameter("partecipante", partecipante);
	      List<Tour> tours=query.getResultList();
	      JSONArray expired= new JSONArray();
	      JSONArray avaiable=new JSONArray();
	      for(Tour t: tours) {
	        if(t.getDataTour().before(date)) {
	          System.out.println("ciao");
	          JSONObject response = null;
	        try {
	          response = new JSONObject();
	          response.put("idTour", t.getIdTour());          
	          response.put("nomeTour", t.getNomeTour());
	          response.put("imgTour", t.getImgTour());
	          response.put("dataTour", t.getDataTour());
	          response.put("durataMinTour", t.getDurataMinTour());
	          response.put("costoTour", t.getCostoTour());
	          response.put("numeroMassimoPartecipantiTour",t.getNumeroMassimoPartecipantiTour());
	          response.put("descrizioneTour", t.getDescrizioneTour());
	          
	        } catch (JSONException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	        }
	          expired.put(response);
	        }else {
	          JSONObject response = null;
	        try {
	          response = new JSONObject();
	          response.put("idTour", t.getIdTour());
	          response.put("nomeTour", t.getNomeTour());
	          response.put("imgTour", t.getImgTour());
	          response.put("dataTour", t.getDataTour().toString().replace("Z[CET]", "").replace("T", " - "));
	          response.put("durataMinTour", t.getDurataMinTour());
	          response.put("costoTour", t.getCostoTour());
	          response.put("numeroMassimoPartecipantiTour",t.getNumeroMassimoPartecipantiTour());
	          response.put("descrizioneTour", t.getDescrizioneTour());
	        } catch (JSONException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	        }
	          avaiable.put(response);
	        }
	      }
//	      PartecipazioniPK key = new PartecipazioniPK();     
//	      key.setUsernamePartecipante(user_partecipant);
//	      Partecipazioni partecipazioni = em.find(Partecipazioni.class, key);
	      em.getTransaction().commit();
	      em.close();
	      emf.close();
	      JSONObject response=new JSONObject();
	      try {
	      response.put("expiredTours", expired);
	      response.put("toDoTours",avaiable);
	      System.out.println(response.toString());
	    } catch (JSONException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	      
	      if(response!=null)
	        return Response.ok()
	          .header("Access-Control-Allow-Origin", "*")
	          .header("Access-Control-Allow-Methods", "GET")
	          .entity(response.toString())
	          .build();
	      else
	        return Response.status(404)
	            .header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").entity(response.toString()).build();
	    }
}

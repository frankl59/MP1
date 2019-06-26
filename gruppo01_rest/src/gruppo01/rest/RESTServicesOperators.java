package gruppo01.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import gruppo01.data.*;

//Sets the path to base URL + /users

//Sets the path to base URL + /users
@Path("/")
@Consumes
public class RESTServicesOperators {	

@GET
  @Path("/operators")
  @Produces("application/json")
  public Response visualizzaOperatori() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    TypedQuery<Operatore> query = em.createQuery("SELECT o FROM Operatore o",Operatore.class);    
    List<Operatore> operatori = query.getResultList();
    em.getTransaction().commit();
    em.close();
    emf.close();
    if(operatori!=null)
    return Response.ok()
        .header("Access-Control-Allow-Origin", "*")
        .header("Access-Control-Allow-Methods", "GET")
        .entity(operatori)
        .build();
    else
    	 return Response.status(404) //404
    		        .header("Access-Control-Allow-Origin", "*")
    		        .header("Access-Control-Allow-Methods", "GET").entity("{}")
    		        .build();
  }
  @GET
    @Path("/operators/{id_operator}")
    @Produces("application/json")
    public Response visualizzaOperatore(
        @PathParam("id_operator") int id_operator) {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
      EntityManager em = emf.createEntityManager();
      em.getTransaction().begin();
      //TypedQuery<Operatore> query = em.createQuery("SELECT o FROM Operatore o where o.",Operatore.class);  
      Operatore operatore = em.find(Operatore.class, id_operator);         
      em.getTransaction().commit();
      em.close();
      emf.close();
      if(operatore!=null)
    	  return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").entity(operatore).build();
      else
    	  return Response.status(404).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").entity("{}").build();
    }
    @GET
    @Path("operators/{id_operator}/tours")
    @Produces("application/json")
    public Response visualizzaTourOperatore(
        @PathParam("id_operator") int id_operator)
        //@QueryParam("order") String order_type) 
    {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
      EntityManager em = emf.createEntityManager();
      em.getTransaction().begin();
      
      Operatore operatore = em.find(Operatore.class, id_operator);
      TypedQuery<Tour> query= em.createQuery("SELECT t FROM Tour t WHERE t.operatore = :operatore",Tour.class);
      
//      if(order_type != null && order_type.compareTo("data") == 0) {
//        query = em2.createQuery("SELECT t FROM Tour r ",Tour.class);
//      }else {
            
      //}
      query.setParameter("operatore", operatore);  
      List<Tour> tours = query.getResultList();    
      em.getTransaction().commit();    
      em.close();  
      emf.close();
      if(tours!=null)
        return Response.ok()
        		.header("Access-Control-Allow-Origin", "*")
        		.header("Access-Control-Allow-Methods", "GET")
        		.entity(tours)
        		.build();
      else
    	  return Response.status(404)//404
  		.header("Access-Control-Allow-Origin", "*")
  		.header("Access-Control-Allow-Methods", "GET").build();
      
    }
    @GET
    @Path("operators/{id_operator}/tours/{id_tour}")
    @Produces("application/json")
    public Response visualizzazioneTour(
      @PathParam("id_operator") int id_operator,
      @PathParam("id_tour") int id_tour) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Operatore operatore = em.find(Operatore.class, id_operator);
        TypedQuery<Tour> query= em.createQuery("SELECT t FROM Tour t WHERE t.operatore = :operatore and t.idTour = :tour",Tour.class);
        query.setParameter("operatore", operatore); 
        query.setParameter("tour", id_tour);  
        List<Tour> tours = query.getResultList(); 
        Tour tour = null;
		if(tours.size() > 0) 
			tour = tours.get(0);	
        em.getTransaction().commit();
        em.close();
        emf.close();
//        JSONObject object = new JSONObject();
//          try {
//        	object.put("nomeTour", tour.getNomeTour());
//			object.put("numeroPartecipanti", tour.getPartecipantes().size());
//			object.put("costoTour", tour.getCostoTour());
//			System.out.println(object);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}        
 //       return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").entity(object.toString()).build();
        if(tour!=null)
        	return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").entity(tour).build();
        else
        	return Response.status(404).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").entity("{}").build();
    }
    
    @POST
    @Path("operators/{id_operator}/tours/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response aggiungiTour(
            @PathParam("id_operator") int id_operator, final JsonObject tour_json) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Operatore operatore = em.find(Operatore.class, id_operator);
        Tour tour = new Tour();
        tour.setOperatore(operatore);
        TourCategoria cat=em.find(TourCategoria.class, tour_json.getInt("tour_category"));
        if (tour_json.getString("tour_date").length() != 0) {
        	System.out.println(tour_json.getString("tour_date"));
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		    LocalDateTime formatDateTime = LocalDateTime.parse(tour_json.getString("tour_date"), formatter);  
        	System.out.println(java.sql.Timestamp.valueOf(tour_json.getString("tour_date")));
//        	tour.setDataTour(java.sql.Timestamp.valueOf(tour_json.getString("tour_date")));
		    tour.setDataTour(java.sql.Timestamp.valueOf(formatDateTime));       	
//		    System.out.println(formatDateTime.toString());
        	System.out.println(tour.getDataTour().toString());
        }
        if (tour_json.getString("tour_description").length() != 0) {
            tour.setDescrizioneTour(tour_json.getString("tour_description"));
        }
        if (tour_json.getString("tour_name").length() != 0) {
            tour.setNomeTour(tour_json.getString("tour_name"));
        }
        if (tour_json.getString("tour_time").length() != 0) {
            tour.setDurataMinTour(Integer.parseInt(tour_json.getString("tour_time")));
        }
        if (tour_json.getString("tour_cost").length() != 0) {
            tour.setCostoTour(Float.parseFloat(tour_json.getString("tour_cost")));
        }
        if (tour_json.getString("tour_maxP").length() != 0) {
            tour.setNumeroMassimoPartecipantiTour(Integer.parseInt(tour_json.getString("tour_maxP")));
        }if (cat!=null) {
            tour.setTourCategoria(cat);
        }
        if(tour_json.getString("tour_img").length()!=0) {
        	tour.setImgTour(tour_json.getString("tour_img"));
        }
        em.persist(tour);
        em.getTransaction().commit();
        em.close();
        emf.close();
        String response = "Tour #" + tour.getIdTour() + " aggiunto da operatore #" + operatore.getIdOperatore();
        System.out.println(response);
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST")
                .entity(response)
                .build();
    }
    
    @POST
    @Path("operators/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response aggiungiOperatore(final JsonObject operator_json) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Operatore operatore=new Operatore();
        //OperatoreTipologia tipo=em.find(OperatoreTipologia.class, Integer.parseInt(operator_json.getString("operator_tipology")));
        OperatoreTipologia tipo=em.find(OperatoreTipologia.class, operator_json.getInt("operator_tipology"));
        if (operator_json.getString("operator_name").length() != 0) {
            operatore.setNomeOperatore(operator_json.getString("operator_name"));
        }
        if (operator_json.getString("operator_email").length() != 0) {
            operatore.setEmailOperatore(operator_json.getString("operator_email"));
        }
        if(tipo!=null)
            operatore.setOperatoreTipologia(tipo);
        
        if (operator_json.getString("operator_phone").length() != 0) {
            operatore.setTelOperatore(operator_json.getString("operator_phone"));
        }
        if (operator_json.getString("operator_pass").length() != 0) {
            operatore.setPasswordOperatore(operator_json.getString("operator_pass"));
        }
        if (operator_json.getString("operator_web").length() != 0) {
            operatore.setWebsiteOperatore(operator_json.getString("operator_web"));
        } 
        System.out.println(operatore.toString());
        em.persist(operatore);
        em.getTransaction().commit();
        em.close();
        emf.close();
        String response = "Operatore #" + operatore.getIdOperatore() + " aggiunto";
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST")
                .entity(response)
                .build();
    }
    
    @PUT
    @Path("operators/{id_operator}/tours/{id_tour}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response modificaTour(
            @PathParam("id_operator") int id_operator,
            @PathParam("id_tour") int id_tour,
            final JsonObject tour_json) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Operatore operatore = em.find(Operatore.class, id_operator);
        Tour tour = em.find(Tour.class, id_tour);
        TourCategoria cat=em.find(TourCategoria.class, tour_json.getInt("tour_category"));
        if (tour != null) {
        	 if (tour_json.getString("tour_date").length() != 0) {
        		 	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        		    LocalDateTime formatDateTime = LocalDateTime.parse(tour_json.getString("tour_date"), formatter);  
        		   
        		    tour.setDataTour(java.sql.Timestamp.valueOf(formatDateTime));       		
        		            }
//            if (tour_json.getString("tour_date").length() != 0) {
//                tour.setDataTour(java.sql.Date.valueOf(tour_json.getString("tour_data")));
//            }
            if (tour_json.getString("tour_description").length() != 0) {
                tour.setDescrizioneTour(tour_json.getString("tour_description"));
            }
            if (tour_json.getString("tour_name").length() != 0) {
                tour.setNomeTour(tour_json.getString("tour_name"));
            }
            if (tour_json.getString("tour_time").length() != 0) {
                tour.setDurataMinTour(Integer.parseInt(tour_json.getString("tour_time")));
            }
            if (tour_json.getString("tour_cost").length() != 0) {
                tour.setCostoTour(Float.parseFloat(tour_json.getString("tour_cost")));
            }
            if (tour_json.getString("tour_maxP").length() != 0) {
                tour.setNumeroMassimoPartecipantiTour(Integer.parseInt(tour_json.getString("tour_maxP")));
            }if (cat!=null) {
                tour.setTourCategoria(cat);
            }
            if(tour_json.getString("tour_img").length()!=0) {
            	tour.setImgTour(tour_json.getString("tour_img"));
            }
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
        if(tour!=null) {
        String response = "Tour #" + tour.getIdTour() + " modificato da operatore #" + operatore.getIdOperatore();
        System.out.println(response);
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "PUT")
                .entity(response)
                .build();
        }else {
        	 return Response.status(404)
                     .header("Access-Control-Allow-Origin", "*")
                     .header("Access-Control-Allow-Methods", "PUT")
                     .build();
        }
    }
        
    @PUT
    @Path("operators/{id_operator}/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response modificaOperatore(@PathParam("id_operator") int id_operator, final JsonObject operator_json) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Operatore operatore = em.find(Operatore.class, id_operator);
        OperatoreTipologia tipo=em.find(OperatoreTipologia.class, operator_json.getInt("operator_tipology"));
        if(operatore!=null) {
        	 if (operator_json.getString("operator_name").length() != 0) {
                 operatore.setNomeOperatore(operator_json.getString("operator_name"));
             }
             if (operator_json.getString("operator_email").length() != 0) {
                 operatore.setEmailOperatore(operator_json.getString("operator_email"));
             }
             if(tipo!=null)
                 operatore.setOperatoreTipologia(tipo);
             
             if (operator_json.getString("operator_phone").length() != 0) {
                 operatore.setTelOperatore(operator_json.getString("operator_phone"));
             }
             if (operator_json.getString("operator_pass").length() != 0) {
                 operatore.setPasswordOperatore(operator_json.getString("operator_pass"));
             }
             if (operator_json.getString("operator_web").length() != 0) {
                 operatore.setWebsiteOperatore(operator_json.getString("operator_web"));
             } 
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        String response = "Operatore #" + operatore.getIdOperatore() + " modificato";
        return Response.ok()
			        .header("Access-Control-Allow-Origin", "*")
			        .header("Access-Control-Allow-Methods", "PUT")
			        .entity(response)
			        .build();
    }
    
    @DELETE
    @Path("operators/{id_operator}/tours/{id_tour}/")
    public Response eliminaTour(
            @PathParam("id_operator") int id_operator,
            @PathParam("id_tour") int id_tour) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Operatore operatore=em.find(Operatore.class, id_operator);
        Tour tour = em.find(Tour.class, id_tour);
        if (tour != null && tour.getOperatore().equals(operatore)) {
            em.remove(tour);
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        if (tour != null && tour.getOperatore().equals(operatore)) {
        	 return Response.ok()
        		        .header("Access-Control-Allow-Origin", "*")
        		        .header("Access-Control-Allow-Methods", "DELETE")
        		        .entity(tour)
        		        .build();
        } else {
            return Response.status(404).header("Access-Control-Allow-Origin", "*")
    		        .header("Access-Control-Allow-Methods", "DELETE").build();
        }
    }

    @DELETE
    @Path("operators/{id_operator}/")
    public Response eliminaOperatore(
            @PathParam("id_operator") int id_operator) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Operatore operatore = em.find(Operatore.class, id_operator);
        if (operatore != null) {
            em.remove(operatore);
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        if (operatore != null) {
        	 return Response.ok()
        		        .header("Access-Control-Allow-Origin", "*")
        		        .header("Access-Control-Allow-Methods", "DELETE")
        		        .entity(operatore)
        		        .build();
        } else {
            return Response.status(404).header("Access-Control-Allow-Origin", "*")
    		        .header("Access-Control-Allow-Methods", "DELETE").build();
        }
    }
    
    @GET
    @Path("operators/{id_operator}/tours/{id_tour}/partecipants/")
    @Produces("application/json")
    public Response visualizzaPartecipanti(
            @PathParam("id_operator") int id_operator,
            @PathParam("id_tour") int id_tour) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Tour tour = em.find(Tour.class, id_tour);
        Operatore operatore=em.find(Operatore.class, id_operator);
        if (tour != null) {
            
        	TypedQuery<Partecipante> query=em.createQuery("SELECT p.partecipante FROM Partecipazioni p WHERE p.tour= :tour",Partecipante.class);
        	//TypedQuery<Partecipante> query=em.createQuery("SELECT p.usernamePartecipante,p.nomePartecipante,p.cognomePartecipante,p.emailPartecipante FROM Partecipante p WHERE p.tours= :tour",Partecipante.class);
//			if(order_type != null && order_type.compareTo("cognome")==0) {
//				query = em2.createQuery("SELECT p FROM Partecipazioni p WHERE p.evento = :evento",Partecipazioni.class);
//			}else {
//				query = em2.createQuery("SELECT p FROM Partecipazioni p WHERE p.evento = :evento ORDER BY p.studente.studenteCognome",Partecipazioni.class);
//			}
            query.setParameter("tour", tour);
            List<Partecipante> partecipazioni = query.getResultList();
            em.getTransaction().commit();          
            em.close();
            emf.close();
//            JSONArray response=null;
//            try {
//				response=new JSONArray(partecipazioni.toString());
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
           
           // String response = "";
            if (partecipazioni.size() > 0 && tour.getOperatore().equals(operatore)) {
            	 return Response.ok()
         		        .header("Access-Control-Allow-Origin", "*")
         		        .header("Access-Control-Allow-Methods", "GET")
         		        .entity(partecipazioni)
         		        .build();
         } 
            }
             return Response.status(404).header("Access-Control-Allow-Origin", "*")//404
     		        .header("Access-Control-Allow-Methods", "DELETE").entity("[]").build();

               
    }
		
}
    

    
   
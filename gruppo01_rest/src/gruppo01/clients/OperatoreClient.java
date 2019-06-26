package gruppo01.clients;

import java.net.URI;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.ClientConfig;

public class OperatoreClient {
	final static String webServiceURI = "http://localhost:8080/gruppo01_rest";
	private static WebTarget target;
	
	public OperatoreClient() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		target = client.target(getBaseURI());
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri(webServiceURI).build();
	}
	
	public JSONObject visualizzaTour(String id_tour,String id_operator) {
		String tour=target.path("operators").path(id_operator).path("tours").path(id_tour).request().accept(MediaType.APPLICATION_JSON).get(String.class);
		JSONObject response=null;
		try {
			response=new JSONObject(tour);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public JSONArray visualizzaTours(String id_operator) {
		String tours=target.path("operators").path(id_operator).path("tours").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		JSONArray response=null;
		try {
			response=new JSONArray(tours);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public String inserisciTour(String id_operator,JSONObject data) {
		System.out.println(data.toString());
		String response = target.path("operators").path(id_operator).path("tours").request().post(Entity.entity(data.toString(),MediaType.APPLICATION_JSON),String.class);
		return response;
	}
	
	public String cancellaTour(String id_operator,String id_tour) {
		String response=target.path("operators").path(id_operator).path("tours").path(id_tour).request().delete().toString();
		return response;
	}
	
	public String modificaTour(String id_operator,String id_tour,JSONObject data) {
		String response = target.path("operators").path(id_operator).path("tours").path(id_tour).request().put(Entity.entity(data.toString(),MediaType.APPLICATION_JSON),String.class);
		return response;
	}
	
	public JSONArray visualizzaPartecipanti(String id_operator,String id_tour) {
		String partecipants=target.path("operators").path(id_operator).path("tours").path(id_tour).path("partecipants").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		JSONArray response=null;
		System.out.println(partecipants);
		try {
			response=new JSONArray(partecipants);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}

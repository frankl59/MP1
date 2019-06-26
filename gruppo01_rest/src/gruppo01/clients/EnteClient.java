package gruppo01.clients;

import java.net.URI;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.ClientConfig;

public class EnteClient {
	final static String webServiceURI = "http://localhost:8080/gruppo01_rest/";
	private static WebTarget target;
	
	public EnteClient() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		target = client.target(getBaseURI());
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri(webServiceURI).build();
	}
	
	
	public JSONArray visualizzaOperatori() {
		String operators=target.path("operators").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		//JSONObject response = null;
		JSONArray response=null;
		try {
			response=new JSONArray(operators);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	public JSONObject visualizzaOperatore(String id_operator) {
		String operator=target.path("operators").path(id_operator).request().accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println(operator);
		JSONObject response = null;
		try {
			response = new JSONObject(operator);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		response.
		//response.optJSONObject(operator);
		return response;
	}
	
	public String aggiungiOperatore(JSONObject data) {
		//System.out.println(data.toString());
		String operator=target.path("operators").request().post(Entity.entity(data.toString(),MediaType.APPLICATION_JSON),String.class);
		return operator;
	}
	public String modificaOperatore(String id_operator,JSONObject data) {
		//System.out.println(data.toString());
		String operator=target.path("operators").path(id_operator).request().put(Entity.entity(data.toString(),MediaType.APPLICATION_JSON),String.class);
		return operator;
	}
	public String cancellaOperatore(String id_operator) {
		String operator=target.path("operators").path(id_operator).request().delete().toString();
		return operator;
		
	}
}

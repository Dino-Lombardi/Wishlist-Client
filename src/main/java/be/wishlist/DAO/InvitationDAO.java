package be.wishlist.DAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import be.wishlist.javabeans.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.wishlist.enums.GiftListStatus;
import be.wishlist.enums.InvitationStatus;
import be.wishlist.javabeans.Invitation;

public class InvitationDAO extends DAO<Invitation>{
	

	public Invitation parseInvitation(JSONObject json) 
	{
		int idinv = json.getInt("id");
		JSONObject sent = json.getJSONObject("sentdate");

		LocalDateTime sentDate = LocalDateTime.of(
		        sent.getInt("year"),
		        sent.getInt("monthValue"),
		        sent.getInt("dayOfMonth"),
		        sent.getInt("hour"),
		        sent.getInt("minute"),
		        sent.getInt("second"),
		        sent.getInt("nano")
		);
		JSONObject jgift = json.getJSONObject("giftlist");
		JSONObject jowner = jgift.getJSONObject("owner");

		InvitationStatus status = InvitationStatus.valueOf(json.getString("status"));
		JSONObject jsonu = json.getJSONObject("user");
		User user = new User(jsonu.getInt("idUser"),jsonu.optString("firstname",null),jsonu.optString("lastname",null),null,null);
		User owner = new User(jowner.getInt("idUser"),jowner.optString("firstname",null),jowner.optString("lastname",null),null,null);
		GiftList gf = new GiftList(jgift.getInt("idgiftlist"),jgift.getString("title"),jgift.getString("description"),null,null,null, jgift.optString("sharelink", null),owner);
		Invitation i = new Invitation(idinv,status, user, gf,sentDate);
		return i;
	}

	public GiftList parseGiftList(JSONObject json) {
	    int id = json.getInt("idgiftlist");
	    String title = json.getString("title");
	    String description = json.getString("description");

	    JSONObject c = json.getJSONObject("creationdate");
	    LocalDate creation = LocalDate.of(c.getInt("year"),c.getInt("monthValue"),c.getInt("dayOfMonth"));

	    JSONObject e = json.getJSONObject("expirationdate");
	    LocalDate expiration = LocalDate.of(e.getInt("year"),e.getInt("monthValue"),e.getInt("dayOfMonth"));

	    GiftListStatus status = GiftListStatus.valueOf(json.getString("status"));

	    String sharelink = null;
	    if(!json.isNull("sharelink"))
	    	sharelink = json.getString("sharelink");

	    JSONObject o = json.getJSONObject("owner");
	    User owner = new User(o.getInt("idUser"),o.getString("firstname"),o.getString("lastname"),o.getString("username"),o.getString("password"));

	    GiftList gl = new GiftList(id, title, description, creation, expiration, status, sharelink, owner);

	    return gl;
	}

	
	@Override
	public boolean create(Invitation obj) {
		
		try 
		{
			JSONObject json = new JSONObject();
			json.put("sent", obj.getSentdate().toString());
			json.put("status", obj.getStatus().toString());
			json.put("userid", obj.getUser().getIdUser());
			json.put("giftlistid", obj.getGiftlist().getIdGiftlist());
			
			ClientResponse response = getResource() 
							.path("Invitation") 
							.type(MediaType.APPLICATION_JSON)
							.post(ClientResponse.class, json.toString());
			
			int status = response.getStatus();
			
			return status == 201;
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			System.out.println("Erreur dans create invitationDAO client");
			return false;
		}
	}

	@Override
	public Invitation find(int id) {
		try 
		{
			String APIResponse = getResource()
					.path("Invitation")
					.path(String.valueOf(id))
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			
			if(APIResponse != null) 
			{
				JSONObject json = new JSONObject(APIResponse);
				Invitation i = parseInvitation(json);
				
				return i;
				
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur dans find invitationDAO client");
			return null;
		}
		return null;
	}

	@Override
	public ArrayList<Invitation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Invitation> findAll(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Invitation obj) {
		    try {
		        JSONObject json = new JSONObject();
		        json.put("sent", obj.getSentdate().toString());
		        json.put("status", obj.getStatus().toString());
		        json.put("userid", obj.getUser().getIdUser());
		        json.put("giftlistid", obj.getGiftlist().getIdGiftlist());

		        ClientResponse rep = getResource()
		                .path("Invitation")
		                .path(String.valueOf(obj.getId()))
		                .type(MediaType.APPLICATION_JSON)
		                .put(ClientResponse.class, json.toString());

		        return rep.getStatus() == 200;

		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		}

	@Override
	public boolean delete(Invitation obj) {
		ClientResponse rep = getResource()
				.path("Invitation")
				.path(String.valueOf(obj.getId()))
				.delete(ClientResponse.class);
		
		return rep.getStatus() == 200;
	}
	
	public ArrayList<Invitation> findUserInvitation(int id)
	{
		try 
		{
			String APIResponse = getResource()
					.path("Invitation")
					.path("user")
					.path(String.valueOf(id))
					.get(String.class);
			
			JSONArray arr = new JSONArray(APIResponse);
			ArrayList<Invitation> invitations = new ArrayList<>();

			for (int i = 0; i < arr.length(); i++) {
			    JSONObject json = arr.getJSONObject(i);
			    
			    Invitation inv = parseInvitation(json);
			    
			    invitations.add(inv);
			}
			
			return invitations;

		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Invitation> findGiftlistInvitation(int id)
	{
		try 
		{
			String APIResponse = getResource()
					.path("Invitation")
					.path("giftlist")
					.path(String.valueOf(id))
					.get(String.class);
			
			JSONArray arr = new JSONArray(APIResponse);
			ArrayList<Invitation> invitations = new ArrayList<>();

			for (int i = 0; i < arr.length(); i++) {
			    JSONObject json = arr.getJSONObject(i);
			    
			    Invitation inv = parseInvitation(json);
			    
			    invitations.add(inv);
			}
			
			return invitations;

		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<GiftList> findInvitedGiftlist(int id)
	{

		try 
		{
			String APIResponse = getResource()
					.path("Invitation")
					.path("accepted")
					.path(String.valueOf(id))
					.get(String.class);
			
			JSONArray arr = new JSONArray(APIResponse);
			ArrayList<GiftList> gf= new ArrayList<>();

			for (int i = 0; i < arr.length(); i++) {
			    JSONObject json = arr.getJSONObject(i);
			    
			    GiftList gl = parseGiftList(json);
			    
			    gf.add(gl);
			    
			}
			return gf;

		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

}

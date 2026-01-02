package be.wishlist.DAO;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;

import be.wishlist.enums.GiftStatus;
import be.wishlist.javabeans.Gift;
import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.Reservation;

public class GiftDAO extends DAO<Gift> 
{

	public Gift parseGift(JSONObject json) 
	{
		JSONObject glJson = json.getJSONObject("giftlist");
		GiftList gl = new GiftList(glJson.getInt("idgiftlist"),null,null,null,null,null,null,null);
		
		
		    Gift gift = new Gift(
		        json.getInt("idGift"),
		        json.getString("name"),
		        json.getString("description"),
		        json.getDouble("price"),
		        json.getInt("priority"),
		        GiftStatus.valueOf(json.getString("status")), 
		        json.optString("image", ""),                 
		        json.getString("buylink"),
		        gl
		    );

		    return gift;

	}
	
	@Override
	public boolean create(Gift obj) {
		try 
		{
			JSONObject json = new JSONObject();
			json.put("name", obj.getName());
			json.put("description", obj.getDescription());
			json.put("price", obj.getPrice());
			json.put("priority", obj.getPriority());
			json.put("status", obj.getStatus());
			json.put("image", obj.getImage());
			json.put("buylink", obj.getBuylink());
			json.put("idgiftlist", obj.getGiftlist().getIdGiftlist());
			
			
			ClientResponse rep = getResource()
					.path("gift")
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class,json.toString());

			return rep.getStatus() == 201;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Gift find(int id) {
		try 
		{
			String APIResponse = getResource()
					.path("gift")
					.path(String.valueOf(id))
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			
			if(APIResponse != null) 
			{
				JSONObject json = new JSONObject(APIResponse);
				Gift g = parseGift(json);
				return g;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Gift> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Gift> findAll(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Gift obj) {
		try 
		{
			JSONObject json = new JSONObject();
			json.put("name", obj.getName());
			json.put("description", obj.getDescription());
			json.put("price", obj.getPrice());
			json.put("priority", obj.getPriority());
			json.put("status", obj.getStatus());
			json.put("image", obj.getImage());
			json.put("buylink", obj.getBuylink());
			json.put("idgiftlist", obj.getGiftlist().getIdGiftlist());
			
			 ClientResponse rep = getResource()
		                .path("gift")
		                .path(String.valueOf(obj.getIdGift()))
		                .type(MediaType.APPLICATION_JSON)
		                .put(ClientResponse.class, json.toString());
			 
			 return rep.getStatus() == 200; 
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Gift obj) {
		try 
		{
			ClientResponse rep = getResource()
					.path("gift")
					.path(String.valueOf(obj.getIdGift()))
					.delete(ClientResponse.class);
			
			return rep.getStatus() == 200;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Gift> getGiftsFromGiftlist(int id)
	{
		try 
		{
			String APIResponse = getResource()
					.path("gift")
					.path("giftlist")
					.path(String.valueOf(id))
					.get(String.class);
			
			JSONArray arr = new JSONArray(APIResponse);
			ArrayList<Gift> gf = new ArrayList<>();

			for (int i = 0; i < arr.length(); i++) {
			    JSONObject json = arr.getJSONObject(i);
			    
			    Gift gl = parseGift(json);
			    
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

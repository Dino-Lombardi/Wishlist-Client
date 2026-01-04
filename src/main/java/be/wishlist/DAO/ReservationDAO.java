package be.wishlist.DAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;

import be.wishlist.javabeans.Reservation;
import be.wishlist.enums.GiftStatus;
import be.wishlist.javabeans.Gift;
import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.Invitation;
import be.wishlist.javabeans.User;

public class ReservationDAO extends DAO<Reservation>{

	public Reservation parseReservation(JSONObject json) 
	{
		try 
		{

			JSONObject dateJson = json.getJSONObject("reservationDate");
			LocalDate date = LocalDate.of(dateJson.getInt("year"),dateJson.getInt("monthValue"),dateJson.getInt("dayOfMonth"));

			double amount = json.getDouble("amount");

			boolean isGroup = json.getBoolean("isgrouppurchase");

			JSONObject userJson = json.getJSONObject("user");
			User user = new User(userJson.getInt("idUser"),userJson.getString("firstname"),userJson.getString("lastname"),userJson.getString("username"),userJson.getString("password"));

			JSONObject giftJson = json.getJSONObject("gift");
			JSONObject giftlistJson = giftJson.getJSONObject("giftlist");
			GiftList gf = new GiftList(giftlistJson.getInt("idgiftlist"),null,null,null,null,null,null,null);
			Gift gift = new Gift(
			    giftJson.getInt("idGift"),
			    giftJson.getString("name"),
			    giftJson.getString("description"),
			    giftJson.getDouble("price"),
			    giftJson.getInt("priority"),
			    GiftStatus.valueOf(giftJson.getString("status")),
			    giftJson.getString("buylink"),
			    giftJson.getString("image"),
			    gf
			);

			return new Reservation(json.getInt("id"),date,amount,isGroup,user,gift);

		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean create(Reservation obj) {
		try 
		{
			JSONObject json = new JSONObject();
			json.put("reservationdate", String.valueOf(obj.getReservationDate()));
			json.put("userid", String.valueOf(obj.getUser().getIdUser()));
			json.put("giftid", String.valueOf(obj.getGift().getIdGift()));
			json.put("amount", String.valueOf(obj.getAmount()));
			json.put("isgroup", String.valueOf(obj.isIsgrouppurchase()));
			
			ClientResponse rep = getResource()
					.path("Reservation")
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
	public Reservation find(int id) {
		try 
		{
			String APIResponse = getResource()
					.path("Reservation")
					.path(String.valueOf(id))
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			
			if(APIResponse != null) 
			{
				JSONObject json = new JSONObject(APIResponse);
				Reservation r = parseReservation(json);
				return r;
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Reservation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Reservation> findAll(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Reservation obj) {
		try 
		{
			JSONObject json = new JSONObject();
			json.put("ReservationDate", String.valueOf(obj.getReservationDate()));
			json.put("userid", String.valueOf(obj.getUser().getIdUser()));
			json.put("giftid", String.valueOf(obj.getGift().getIdGift()));
			json.put("amount", String.valueOf(obj.getAmount()));
			json.put("isgroup", String.valueOf(obj.isIsgrouppurchase()));
			
			 ClientResponse rep = getResource()
		                .path("Reservation")
		                .path(String.valueOf(obj.getId()))
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
	public boolean delete(Reservation obj) {
		try 
		{
			ClientResponse rep = getResource()
					.path("Reservation")
					.path(String.valueOf(obj.getId()))
					.delete(ClientResponse.class);
			
			return rep.getStatus() == 200;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Reservation> findUserReservations(int id)
	{
	    try 
	    {
	        ClientResponse rep = getResource()
	                .path("Reservation")
	                .path("user")
	                .path(String.valueOf(id))
	                .get(ClientResponse.class);

	        int status = rep.getStatus();

	        if (status == 204) {
	            return new ArrayList<>();
	        }

	        if (status != 200) {
	            return null;
	        }

	        String APIResponse = rep.getEntity(String.class);

	        JSONArray arr = new JSONArray(APIResponse);
	        ArrayList<Reservation> res = new ArrayList<>();

	        for (int i = 0; i < arr.length(); i++) {
	            JSONObject json = arr.getJSONObject(i);
	            Reservation inv = parseReservation(json);
	            res.add(inv);
	        }

	        return res;
	    }
	    catch(Exception e) 
	    {
	        e.printStackTrace();
	    }
	    return null;
	}

	
	public ArrayList<Reservation> findGiftReservations(int id)
	{
	    try 
	    {
	        ClientResponse rep = getResource()
	                .path("Reservation")
	                .path("gift")
	                .path(String.valueOf(id))
	                .get(ClientResponse.class);

	        int status = rep.getStatus();


	        if (status == 204) {
	            return new ArrayList<>();
	        }


	        if (status != 200) {
	            return null;
	        }


	        String APIResponse = rep.getEntity(String.class);

	        JSONArray arr = new JSONArray(APIResponse);
	        ArrayList<Reservation> res = new ArrayList<>();

	        for (int i = 0; i < arr.length(); i++) {
	            JSONObject json = arr.getJSONObject(i);
	            Reservation inv = parseReservation(json);
	            res.add(inv);
	        }

	        return res;
	    }
	    catch(Exception e) 
	    {
	        e.printStackTrace();
	    }
	    return null;
	}


}

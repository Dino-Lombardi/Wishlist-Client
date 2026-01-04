package be.wishlist.DAO;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;

import be.wishlist.enums.GiftListStatus;
import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.User;

public class GiftListDAO extends DAO<GiftList> {

	@Override
	public boolean create(GiftList obj) {
		ClientResponse res;
		
		try {
			JSONObject giftlistJSON = new JSONObject();
			giftlistJSON.put("title", obj.getTitle());
			giftlistJSON.put("description", obj.getDescription());
			JSONObject expirationdateJSON = new JSONObject();
			expirationdateJSON.put("year", obj.getExpirationDate().getYear());
			expirationdateJSON.put("monthValue", obj.getExpirationDate().getMonthValue());
			expirationdateJSON.put("dayOfMonth", obj.getExpirationDate().getDayOfMonth());
			giftlistJSON.put("expirationdate", expirationdateJSON);
			giftlistJSON.put("status", obj.getStatus().toString());
			giftlistJSON.put("sharelink", obj.getSharelink());
			giftlistJSON.put("idowner", obj.getOwner().getIdUser());
			
			res = getResource()
					.path("giftlist")
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, giftlistJSON.toString());
			
			if (res.getStatus() == 201) 
				return true;
			
		} catch (Exception e) {
			System.out.println("Exception dans GiftListDAO");
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}

	@Override
	public GiftList find(int id) {
		
		ClientResponse res;
		GiftList giftlist = null;
		
		try {
			
			res = getResource()
					.path("giftlist")
					.path(String.valueOf(id))
					.type(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			
			if (res.getStatus() == 200) {
				JSONObject giftlistJSON = new JSONObject(res.getEntity(String.class));
				int idGiftList = giftlistJSON.getInt("idgiftlist");
				String title = giftlistJSON.getString("title");
				String description = null;
				if(!giftlistJSON.isNull("description")){
					description = giftlistJSON.getString("description");
				}
				
				JSONObject creationdate = giftlistJSON.getJSONObject("creationdate");
				int year = creationdate.getInt("year");
				int month = creationdate.getInt("monthValue");
				int day = creationdate.getInt("dayOfMonth");
				LocalDate creationDate = LocalDate.of(year, month, day);
				
				JSONObject expirationdate = giftlistJSON.getJSONObject("expirationdate");
				year = expirationdate.getInt("year");
				month = expirationdate.getInt("monthValue");
				day = expirationdate.getInt("dayOfMonth");
				LocalDate expirationDate = LocalDate.of(year, month, day);
				
				String status = giftlistJSON.getString("status");
				GiftListStatus giftListStatus = GiftListStatus.valueOf(status);
				
				String sharelink = null;
				if(!giftlistJSON.isNull("sharelink")) {
					sharelink = giftlistJSON.getString("sharelink");
				}
				JSONObject userJSON = giftlistJSON.getJSONObject("owner");
				User user = new User();
				user.setIdUser(userJSON.getInt("idUser"));
				giftlist = new GiftList(idGiftList, title, description, creationDate, expirationDate, giftListStatus, sharelink, user);				
			}
			
		} catch (Exception e) {
			System.out.println("Exception dans GiftListDAO - find");
			System.out.println(e.getMessage());
			return null;
		}
		
		return giftlist;
	}

	@Override
	public ArrayList<GiftList> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<GiftList> findAll(int id) {
		ArrayList<GiftList> giftlists = null;
		
		String APIResponse = getResource()
				.path("giftlist")
				.path("user")
				.path(String.valueOf(id))
				.type(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		if(APIResponse != null) {
			JSONArray array = new JSONArray(APIResponse); 
			giftlists = new ArrayList<GiftList>();
			try {
				for (int i = 0; i < array.length(); i++) {
					JSONObject giftlistJSON = array.getJSONObject(i);
					int idGiftList = giftlistJSON.getInt("idgiftlist");
					String title = giftlistJSON.getString("title");
					String description = null;
					if(!giftlistJSON.isNull("description")){
						description = giftlistJSON.getString("description");
					}					
					JSONObject creationdate = giftlistJSON.getJSONObject("creationdate");
					int year = creationdate.getInt("year");
					int month = creationdate.getInt("monthValue");
					int day = creationdate.getInt("dayOfMonth");
					LocalDate creationDate = LocalDate.of(year, month, day);
					
					JSONObject expirationdate = giftlistJSON.getJSONObject("expirationdate");
					year = expirationdate.getInt("year");
					month = expirationdate.getInt("monthValue");
					day = expirationdate.getInt("dayOfMonth");
					LocalDate expirationDate = LocalDate.of(year, month, day);
					
					String status = giftlistJSON.getString("status");
					GiftListStatus giftListStatus = GiftListStatus.valueOf(status);
					
					String sharelink = null;
					if(!giftlistJSON.isNull("sharelink")) {
						sharelink = giftlistJSON.getString("sharelink");
					}					
					User user = new User();
					user.setIdUser(id);
					GiftList giftlist = new GiftList(idGiftList, title, description, creationDate, expirationDate, giftListStatus, sharelink, user);
					giftlists.add(giftlist);
				}
			} catch (Exception e) {
				System.out.println("Exception dans GiftListDAO - findAll(id)");
				System.out.println(e.getMessage());
				return null;
			}
		}
		return giftlists;
	}

	@Override
	public boolean update(GiftList obj) {
		ClientResponse res;
		
		try {
			JSONObject giftlistJSON = new JSONObject();
			giftlistJSON.put("title", obj.getTitle());
			if(obj.getDescription() != null) {
				giftlistJSON.put("description", obj.getDescription());

			}
			JSONObject expirationdateJSON = new JSONObject();
			expirationdateJSON.put("year", obj.getExpirationDate().getYear());
			expirationdateJSON.put("monthValue", obj.getExpirationDate().getMonthValue());
			expirationdateJSON.put("dayOfMonth", obj.getExpirationDate().getDayOfMonth());
			giftlistJSON.put("expirationdate", expirationdateJSON);
			giftlistJSON.put("status", obj.getStatus().toString());
			if(obj.getSharelink() != null) {
				giftlistJSON.put("sharelink", obj.getSharelink());
			}
			
			res = getResource()
					.path("giftlist")
					.path(String.valueOf(obj.getIdGiftlist()))
	                .header("Content-Type", "application/json;charset=UTF-8")
					.put(ClientResponse.class, giftlistJSON.toString());
			
			if (res.getStatus() == 204) 
				return true;
			
		} catch (Exception e) {
			System.out.println("Exception dans GiftListDAO");
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}
	@Override
	public boolean delete(GiftList obj) {
		ClientResponse res;
		
		res = getResource()
				.path("giftlist")
				.path(String.valueOf(obj.getIdGiftlist()))
				.delete(ClientResponse.class);
		
		return res.getStatus() == 204;
	}

}

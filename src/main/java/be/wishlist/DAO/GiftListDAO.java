package be.wishlist.DAO;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import be.wishlist.enums.GiftListStatus;
import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.User;

public class GiftListDAO extends DAO<GiftList> {

	@Override
	public boolean create(GiftList obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GiftList find(int id) {
		// TODO Auto-generated method stub
		return null;
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
				.accept("application/json")
				.get(String.class);
		
		if(APIResponse != null) {
			JSONArray array = new JSONArray(APIResponse); 
			giftlists = new ArrayList<GiftList>();
			try {
				for (int i = 0; i < array.length(); i++) {
					JSONObject giftlistJSON = array.getJSONObject(i);
					int idGiftList = giftlistJSON.getInt("idgiftlist");
					String title = giftlistJSON.getString("title");
					String description = giftlistJSON.getString("description");
					
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
					
					String sharelink = giftlistJSON.getString("sharelink");
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(GiftList obj) {
		// TODO Auto-generated method stub
		return false;
	}

}

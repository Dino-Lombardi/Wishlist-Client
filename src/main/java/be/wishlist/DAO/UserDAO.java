package be.wishlist.DAO;

import java.util.ArrayList;

import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;

import be.wishlist.javabeans.User;

public class UserDAO extends DAO<User>{

	@Override
	public boolean create(User obj) {
		ClientResponse res;
		
		try {
			JSONObject userJSON = new JSONObject();
			userJSON.put("firstname", obj.getFirstname());
			userJSON.put("lastname", obj.getLastname());
			userJSON.put("username", obj.getUsername());
			userJSON.put("password", obj.getPassword());
			res = getResource()
					.path("user")
					.type("application/json")
					.accept("application/json")
					.post(ClientResponse.class, userJSON.toString());
			
			if (res.getStatus() == 201) 
				return true;
			
		} catch (Exception e) {
			System.out.println("Exception dans UserDAO");
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}
		

	@Override
	public User find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User find(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

}

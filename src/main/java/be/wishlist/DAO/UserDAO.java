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
		
		ClientResponse res;
		User user = null;
		
		try {
			JSONObject loginuserJSON = new JSONObject();
			loginuserJSON.put("username", username);
			loginuserJSON.put("password",  password);
			
			res = getResource()
					.path("user")
					.path("login")
	                .header("Content-Type", "application/json;charset=UTF-8")
					.post(ClientResponse.class, loginuserJSON.toString());
			
			if (res.getStatus() == 200) {
				JSONObject userJSON = new JSONObject(res.getEntity(String.class));
				int iduser = userJSON.getInt("idUser");
				String firstname = userJSON.getString("firstname");
				String lastname = userJSON.getString("lastname");
				String usrname = userJSON.getString("username");
				String pwd = userJSON.getString("password");
				
				user = new User(iduser, firstname, lastname, usrname, pwd);
			}
			
		} catch (Exception e) {
			System.out.println("Exception dans UserDAO - find pour le login");
			System.out.println(e.getMessage());
			return null;
		}
		
		return user;
	}

	@Override
	public ArrayList<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<User> findAll(int id) {
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

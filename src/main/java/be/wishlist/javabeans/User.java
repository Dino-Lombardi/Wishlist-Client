package be.wishlist.javabeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.wishlist.DAO.DAO;
import be.wishlist.DAO.UserDAO;
import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 5096403742888293701L;
	private static final DAO<User> userDAO = new UserDAO();
	
	private int iduser;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private ArrayList<GiftList> giftlists;
	private ArrayList<Invitation> invitations;
	
	public User() {
		giftlists = new ArrayList<GiftList>();
		invitations = new ArrayList<Invitation>();
	}
	
	public User(int iduser, String firstname, String lastname, String username, String password) {
		this.iduser = iduser;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		
		giftlists = new ArrayList<GiftList>();
		invitations = new ArrayList<Invitation>();
	}

	public int getIdUser() {
		return iduser;
	}

	public void setIdUser(int iduser) {
		this.iduser = iduser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public ArrayList<GiftList> getGiftlists() {
		return giftlists;
	}

	public void setGiftlists(ArrayList<GiftList> giftlists) {
		this.giftlists = giftlists;
	}
	
	public void addGiftlist(GiftList giftlist) {
		this.giftlists.add(giftlist);
	}
	
	public void removeGiftlist(GiftList giftlist) {
		this.giftlists.remove(giftlist);
	}

	public ArrayList<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(ArrayList<Invitation> invitations) {
		this.invitations = invitations;
	}
	
	public void addInvitation(Invitation invitation) {
		this.invitations.add(invitation);
	}
	
	public void removeInvitation(Invitation invitation) {
		this.invitations.remove(invitation);
	}
	
	// Méthodes business
	
	public Map<String, String> CreateUserValidation(String confirmPassword){
		Map<String, String> errors = new HashMap<String, String>();
		if(this.firstname == null || this.firstname.trim().isEmpty()) 
			errors.put("firstname", "Le prénom est obligatoire.");
			
		if(this.lastname == null || this.lastname.trim().isEmpty()) 
			errors.put("lastname", "Le nom est obligatoire.");
		
		if(this.username == null || this.username.trim().isEmpty())
			errors.put("username", "Le nom d'utilisateur est obligatoire.");
		else if(!username.matches("^[0-9a-zA-Z]{4,}$"))
			errors.put("username", "Le nom d'utilisateur doit contenir au moins 4 caractères.");
		
		if(this.password == null || this.password.trim().isEmpty())
			errors.put("password", "Le mot de passe est obligatoire.");
		else if(!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"))
			errors.put("password", "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre.");
		
		if(confirmPassword == null || confirmPassword.trim().isEmpty())
			errors.put("confirmPassword", "La confirmation du mot de passe est obligatoire.");
		else if(!this.password.equals(confirmPassword))
			errors.put("confirmPassword", "Le mot de passe et sa confirmation ne correspondent pas.");
		
		return errors;
	}
	
	// Méthodes CRUD + login
	
	public boolean insert() {
		return userDAO.create(this);
	}
	
	public boolean update() {
		return userDAO.update(this);
	}
	
	public boolean delete() {
		return userDAO.delete(this);
	}
	
	public static User getUser(int id) {
		return userDAO.find(id);
	}
	
	public static User getUser(String username) {
		return  ((UserDAO) userDAO).find(username);
	}
	
	public static ArrayList<User> getUsers(){
		return userDAO.findAll();
	}
	
	public static User login (String username, String password) {
		return ((UserDAO) userDAO).find(username, password);
	}
	
	public boolean fetchGiftlists() {
		this.giftlists = GiftList.getGiftListsByUser(this);
		return giftlists != null;
	}
	
}

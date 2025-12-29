package be.wishlist.DAO;

import java.net.URI;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public abstract class DAO<T> {

	private WebResource resource = null;
	private Client client = null;
	private static final String  API_URL = "http://localhost:8080/Wishlist-API/api";

    public DAO() {
    	ClientConfig config = new DefaultClientConfig();
    	client = Client.create(config);
		resource = client.resource(getBaseURI());
    }
    
	private static URI getBaseURI() {
		return UriBuilder.fromUri(API_URL).build();
	}
	
	public WebResource getResource() {
		return resource;
	}

    public abstract boolean create(T obj);
    
    public abstract T find(int id);
    
    public abstract ArrayList<T> findAll();
    
    public abstract ArrayList<T> findAll(int id);
    
    public abstract boolean update(T obj);

    public abstract boolean delete(T obj);
}

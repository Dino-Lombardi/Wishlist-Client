package be.wishlist.DAO;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public abstract class DAO<T> {

	private WebResource resource = null;
	private Client client = null;

    public DAO() {
    	ClientConfig config = new DefaultClientConfig();
    	client = Client.create(config);
		resource = client.resource(getBaseURI());
    }
    
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/Wishlist-API/api").build();
	}
	
	public WebResource getResource() {
		return resource;
	}

    public abstract boolean create(T obj);
    
    public abstract T find(int id);
    
    public abstract ArrayList<T> findAll();
    
    public abstract boolean update(T obj);

    public abstract boolean delete(T obj);
}

package be.wishlist.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.User;


public class CreateGiftList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CreateGiftList() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/WEB-INF/JSP/createGiftList.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<>();
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("connectedUser");
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String expirationdateStr = request.getParameter("expirationdate");
        String statusStr = request.getParameter("status");	
        
        
        GiftList giftlist = new GiftList();
        
        try {
        	 giftlist.setTitle(title);
             giftlist.setDescription(description);
             giftlist.setExpirationDate(GiftList.parseEnglishDate(expirationdateStr));
             giftlist.setStatus(GiftList.parseGiftListStatus(statusStr));
             giftlist.setOwner(user);
             
             messages = giftlist.validate();
             
             if(messages.isEmpty()) {
             	if(giftlist.insert()) {
     				session.setAttribute("successMessage", "Création de la liste réussie !");
     				response.sendRedirect(request.getContextPath() + "/home");
     			} else {
     				messages.put("error", "Un problème est survenu lors de la création de votre liste, veuillez réessayer");
     		        request.setAttribute("messages", messages);
     				getServletContext().getRequestDispatcher("/WEB-INF/JSP/createGiftList.jsp").forward(request, response);
     			}
             }
             else {
             	request.setAttribute("messages", messages);
 				getServletContext().getRequestDispatcher("/WEB-INF/JSP/createGiftList.jsp").forward(request, response);
             }
        } catch(Exception e ) {
            getServletContext().getRequestDispatcher("/WEB-INF/JSP/errorPage.jsp").forward(request, response);
            e.printStackTrace();

        }
	}
}

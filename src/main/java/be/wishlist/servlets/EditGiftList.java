package be.wishlist.servlets;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.User;


public class EditGiftList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public EditGiftList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			int idgiftlist = Integer.parseInt(request.getParameter("id"));
			GiftList giftlist = GiftList.find(idgiftlist);
			User user = (User) session.getAttribute("connectedUser");
			
			if(giftlist.getOwner().getIdUser() != user.getIdUser()) {
				throw new ServletException("Vous n'êtes pas le propriétaire de cette liste de cadeaux.");
			}
			
			request.setAttribute("giftlist", giftlist);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/editGiftList.jsp").forward(request, response);
		} catch(Exception e) {
            getServletContext().getRequestDispatcher("/WEB-INF/JSP/errorPage.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<>();
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("connectedUser");
        
        try 
        {
            int idgiftlist = Integer.parseInt(request.getParameter("id"));
            GiftList giftlist = GiftList.find(idgiftlist);
            
            if(giftlist.getOwner().getIdUser() != user.getIdUser()) {
				throw new ServletException("Vous n'êtes pas le propriétaire de cette liste de cadeaux.");

            }
            
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String expirationdateStr = request.getParameter("expirationdate");
            String statusStr = request.getParameter("status");	
            
            
            giftlist.setTitle(title);
            giftlist.setDescription(description);
            giftlist.setExpirationDate(GiftList.parseEnglishDate(expirationdateStr));
            giftlist.setStatus(GiftList.parseGiftListStatus(statusStr));
            
            messages = giftlist.validate();
            
            if(messages.isEmpty()) {
            	if(giftlist.update()) {
    				session.setAttribute("successMessage", "Modification de la liste réussie !");
    				response.sendRedirect(request.getContextPath() + "/home");
    			} else {
    				messages.put("error", "Un problème est survenu lors de la modification de votre liste, veuillez réessayer");
    		        request.setAttribute("messages", messages);
    				getServletContext().getRequestDispatcher("/WEB-INF/JSP/editGiftList.jsp").forward(request, response);
    			}
            }
            else {
            	request.setAttribute("messages", messages);
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/editGiftList.jsp").forward(request, response);
            }
        } catch(Exception e ) {
            getServletContext().getRequestDispatcher("/WEB-INF/JSP/errorPage.jsp").forward(request, response);
            e.printStackTrace();
        }
	}
}


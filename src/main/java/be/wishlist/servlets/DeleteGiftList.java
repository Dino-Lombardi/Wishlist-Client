package be.wishlist.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.User;

public class DeleteGiftList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public DeleteGiftList() {
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
			
			if(giftlist.delete()) {
				session.setAttribute("successMessage", "Suppression de la liste réussie !");
				response.sendRedirect(request.getContextPath() + "/home");
			} else {
		        request.setAttribute("errorMessage", "Echec de la suppression de la liste.");
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/home.jsp").forward(request, response);
			}
		} catch(Exception e) {
            getServletContext().getRequestDispatcher("/WEB-INF/JSP/errorPage.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

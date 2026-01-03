package be.wishlist.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.wishlist.enums.GiftListStatus;
import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.User;

public class ViewGiftList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ViewGiftList() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			int idgiftlist = Integer.parseInt(request.getParameter("id"));
			GiftList giftlist = GiftList.find(idgiftlist);
			
			if(giftlist.isDateExpired() || giftlist.getStatus() == GiftListStatus.EXPIRED) {
				session.setAttribute("errorMessage", "Cette liste de cadeaux est expirée et ne peut pas être modifiée.");
	            response.sendRedirect(request.getContextPath() + "/home");
	            return;
			}
			User user = (User) session.getAttribute("connectedUser");
			
			if(giftlist.getOwner().getIdUser() != user.getIdUser()) {
				throw new ServletException("Vous n'êtes pas le propriétaire de cette liste de cadeaux.");
			}
			
			if(giftlist.fetchGifts()) {
				giftlist.sortGiftsByPriority();
				request.setAttribute("giftlist", giftlist);
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/viewGiftList.jsp").forward(request, response);
			}
			
		} catch(Exception e) {
            getServletContext().getRequestDispatcher("/WEB-INF/JSP/errorPage.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

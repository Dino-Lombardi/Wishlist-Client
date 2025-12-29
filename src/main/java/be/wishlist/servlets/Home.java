package be.wishlist.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.wishlist.enums.GiftListStatus;
import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.User;

public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Home() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User)session.getAttribute("connectedUser");
		user.fetchGiftlists();
		request.setAttribute("giftlists", user.getGiftlists());
		request.setAttribute("user", user);
		request.getRequestDispatcher("/WEB-INF/JSP/home.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

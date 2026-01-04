package be.wishlist.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.Invitation;
import be.wishlist.javabeans.User;


public class InvitedGiftlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InvitedGiftlist() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("connectedUser");
		
		if (user == null) 
		{
			response.sendRedirect("loginUser.jsp");
			return;
		}
		
		ArrayList<GiftList> gl = Invitation.findInvitedGiftlist(user.getIdUser()); 
		
		if (gl == null) 
		{
			gl = new ArrayList<GiftList>();
		}
		
		request.setAttribute("giftlist", gl);
		request.getRequestDispatcher("/WEB-INF/JSP/InvitedGiftlist.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

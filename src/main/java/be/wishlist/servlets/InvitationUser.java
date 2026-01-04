package be.wishlist.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.wishlist.enums.InvitationStatus;
import be.wishlist.javabeans.Invitation;
import be.wishlist.javabeans.User;


public class InvitationUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public InvitationUser() {
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
		
		ArrayList<Invitation> inv = Invitation.findUserInvitations(user.getIdUser());
		
		if (inv == null) 
		{
			inv = new ArrayList<Invitation>();
		}
		else 
		{
			for(Invitation i : inv) 
			{
				if(i.getStatus() == InvitationStatus.PENDING) 
				{
					i.setStatus(InvitationStatus.VIEWED);
				}
				
				Invitation.update(i);
				user.addInvitation(i);
			}
		}
		
		
		request.setAttribute("invitations", inv);
		request.getRequestDispatcher("/WEB-INF/JSP/Invitations.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package be.wishlist.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.wishlist.enums.InvitationStatus;
import be.wishlist.javabeans.Invitation;


public class UpdateInvitation extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateInvitation() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 try {
	            int id = Integer.parseInt(request.getParameter("id"));
	            String action = request.getParameter("action");
	            
	            Invitation inv = Invitation.find(id);

	            if (inv == null) {
	                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invitation introuvable");
	                return;
	            }


	            switch (action) {
	                case "accept":
	                    inv.setStatus(InvitationStatus.ACCEPTED);
	                    break;

	                case "reject":
	                    Invitation.delete(inv);
	                    break;

	                default:
	                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue");
	                    return;
	            }

	            Invitation.update(inv);

	            response.sendRedirect(request.getContextPath() + "/home/invitationuser");

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne");
	        }
	}

}

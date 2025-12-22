package be.wishlist.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.wishlist.javabeans.User;


public class CreateUserAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

   
    public CreateUserAccount() {
    	super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/JSP/createUserAccount.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        User user = new User(0,firstname, lastname, username, password);
        messages = user.CreateUserValidation(confirmPassword);
        
        if(messages.isEmpty()) {
			if(user.insert()) {
				response.sendRedirect(request.getContextPath() + "/Login");
			} else {
				messages.put("error", "Un problème est survenu lors de la création de votre compte. Veuillez réessayer.");
		        request.setAttribute("messages", messages);
				request.getRequestDispatcher("/WEB-INF/JSP/createUserAccount.jsp").forward(request, response);
			}
		} else {
	        request.setAttribute("messages", messages);
			request.getRequestDispatcher("/WEB-INF/JSP/createUserAccount.jsp").forward(request, response);
        }

	}

}

package be.wishlist.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.wishlist.javabeans.User;


public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Login() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			if(session.getAttribute("connectedUser") != null) {
				response.sendRedirect(request.getContextPath() + "/home");
				return;
			}
			
			else if(session.getAttribute("successMessage") != null) {
				request.setAttribute("successMessage", (String)session.getAttribute("successMessage"));
				session.removeAttribute("successMessage");
			}
		}

		
		request.getRequestDispatcher("/WEB-INF/JSP/loginUser.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = User.login(username, password);
		
		if(user != null) {
			request.getSession().setAttribute("connectedUser", user);
			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			request.setAttribute("error", "Nom d'utilisateur ou mot de passe incorrect.");
			request.getRequestDispatcher("/WEB-INF/JSP/loginUser.jsp").forward(request, response);
		}
	}
}
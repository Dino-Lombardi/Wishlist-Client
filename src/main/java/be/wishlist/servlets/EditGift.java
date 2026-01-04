package be.wishlist.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.wishlist.javabeans.Gift;

/**
 * Servlet implementation class EditGift
 */
@WebServlet("/EditGift")
public class EditGift extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EditGift() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String idStr = request.getParameter("id");
            
            if (idStr == null) 
            {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            int idGift = Integer.parseInt(idStr);

            Gift gift = Gift.getGift(idGift);
            
            if (gift == null) 
            {
            	
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            request.setAttribute("gift", gift);

            request.getRequestDispatcher("/WEB-INF/JSP/EditGift.jsp").forward(request, response);

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

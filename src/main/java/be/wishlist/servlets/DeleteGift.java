package be.wishlist.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.wishlist.javabeans.Gift;


public class DeleteGift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteGift() {
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

            int idGiftList = gift.getGiftlist().getIdGiftlist();
            System.out.println(idGiftList);
            
            boolean ok = gift.delete();

            if (ok) 
            {
                response.sendRedirect(request.getContextPath() + "/home/viewgiftlist?id=" + idGiftList);
            } 
            else 
            {
                request.setAttribute("error", "Impossible de supprimer le cadeau.");
                response.sendRedirect(request.getContextPath() + "/home/viewgiftlist?id=" + idGiftList);
            }
			
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

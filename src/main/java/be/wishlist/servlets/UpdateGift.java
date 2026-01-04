package be.wishlist.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import be.wishlist.javabeans.Gift;

@MultipartConfig
public class UpdateGift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateGift() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
        	
            int idGift = Integer.parseInt(request.getParameter("idgift"));

            Gift gift = Gift.getGift(idGift);
       

            if (gift == null) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            gift.setName(request.getParameter("name"));
            gift.setDescription(request.getParameter("description"));
            gift.setPrice(Double.parseDouble(request.getParameter("price")));
            gift.setPriority(Integer.parseInt(request.getParameter("priority")));
            gift.setBuylink(request.getParameter("buylink"));

            Part imagePart = request.getPart("image");
            System.out.println("imagePart = " + imagePart);
            System.out.println("image size = " + (imagePart != null ? imagePart.getSize() : "null"));
            System.out.println("idgift = " + request.getParameter("idgift"));
            if (imagePart != null && imagePart.getSize() > 0) 
            {
                InputStream inputStream = imagePart.getInputStream();
                byte[] bytes = inputStream.readAllBytes();
                String base64Image = Base64.getEncoder().encodeToString(bytes);
                gift.setImage(base64Image);
            }

            boolean ok = gift.update();

            int idGiftList = gift.getGiftlist().getIdGiftlist();

            if (ok) 
            {
                response.sendRedirect(request.getContextPath() + "/home/viewgiftlist?id=" + idGiftList);
            } 
            else 
            {
                request.setAttribute("error", "Impossible de mettre à jour le cadeau.");
                response.sendRedirect(request.getContextPath() + "/home/editgift?id=" + idGift);
            }

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}

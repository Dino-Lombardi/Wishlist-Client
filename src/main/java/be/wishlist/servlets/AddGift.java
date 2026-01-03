package be.wishlist.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import be.wishlist.enums.GiftStatus;
import be.wishlist.javabeans.Gift;
import be.wishlist.javabeans.GiftList;

@MultipartConfig
public class AddGift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddGift() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String readPartAsString(Part part) throws IOException {
        if (part == null) return null;
        return new String(part.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("idgiftlist"));
        GiftList gl = GiftList.find(id);
        request.setAttribute("giftlist", gl);

        request.getRequestDispatcher("/WEB-INF/JSP/AddGift.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        String name = readPartAsString(request.getPart("name"));
        String description = readPartAsString(request.getPart("description"));
        String priceStr = readPartAsString(request.getPart("price"));
        String priorityStr = readPartAsString(request.getPart("priority"));
        String buylink = readPartAsString(request.getPart("buylink"));
        String idGiftListStr = readPartAsString(request.getPart("idgiftlist"));

        double price = Double.parseDouble(priceStr);
        int priority = Integer.parseInt(priorityStr);
        int idgiftlist = Integer.parseInt(idGiftListStr);

        GiftStatus status = GiftStatus.AVAILABLE;

        Part imagePart = request.getPart("image");
        String imageBase64 = null;

        if (imagePart != null && imagePart.getSize() > 0) {
            byte[] bytes = imagePart.getInputStream().readAllBytes();
            imageBase64 = Base64.getEncoder().encodeToString(bytes);
        }

        GiftList gl = GiftList.find(idgiftlist);

        Gift gift = new Gift(name, description, price, priority, status, imageBase64, buylink, gl);
        boolean ok = gift.insert();
        
        if (!ok) {
            request.setAttribute("error", "Impossible d'ajouter le cadeau.");
            request.setAttribute("giftlist", gl);
            request.getRequestDispatcher("/WEB-INF/JSP/AddGift.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/home");
    }


}

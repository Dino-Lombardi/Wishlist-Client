package be.wishlist.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.wishlist.enums.GiftStatus;
import be.wishlist.javabeans.Gift;
import be.wishlist.javabeans.Reservation;
import be.wishlist.javabeans.User;


public class ReserveGift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ReserveGift() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idGift = Integer.parseInt(request.getParameter("id"));
        Gift gift = Gift.getGift(idGift);

        request.setAttribute("gift", gift);
        request.getRequestDispatcher("/WEB-INF/JSP/ReserveGift.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("connectedUser");

        int idGift = Integer.parseInt(request.getParameter("idgift"));
        Gift gift = Gift.getGift(idGift);

        boolean isGroup = request.getParameter("isgroup") != null 
                          || "true".equals(request.getParameter("group"));

        double totalReserved = Reservation.getTotalAmountReserved(gift);
        double remaining = gift.getPrice() - totalReserved;
        
        if (remaining < 0) remaining = 0;

        String amountStr = request.getParameter("amount");
        double amount = 0;

        try {
            amount = Double.parseDouble(amountStr);
        } catch (Exception e) {
            amount = 0;
        }

        if (isGroup) {
            if (amount <= 0 || amount > remaining) {
                request.setAttribute("error", "Montant invalide.");
                request.setAttribute("gift", gift);
                request.getRequestDispatcher("/WEB-INF/JSP/ReserveGift.jsp").forward(request, response);
                return;
            }
        } else {
            amount = gift.getPrice();
        }

        Reservation reservation = new Reservation(LocalDate.now(), amount, isGroup, user, gift);
        boolean ok = Reservation.create(reservation);

        if (ok) {

            double newTotal = totalReserved + amount;

            if (newTotal >= gift.getPrice()) 
            {
                gift.setStatus(GiftStatus.FULLY_RESERVED);
            } 
            else 
            {
                gift.setStatus(GiftStatus.PARTIALLY_RESERVED);
            }

            gift.update();

            gift = Gift.getGift(idGift);

            response.sendRedirect(request.getContextPath() +
                    "/home/viewgiftlist?id=" + gift.getGiftlist().getIdGiftlist());
        }
         else {
            request.setAttribute("error", "Impossible de réserver ce cadeau.");
            request.setAttribute("gift", gift);
            request.getRequestDispatcher("/WEB-INF/JSP/ReserveGift.jsp").forward(request, response);
        }
    }



}

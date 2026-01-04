package be.wishlist.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.wishlist.javabeans.Gift;
import be.wishlist.javabeans.Reservation;

public class DeleteReservation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteReservation() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idReservation = Integer.parseInt(request.getParameter("id"));

            Reservation reservation = Reservation.find(idReservation);

            if (reservation == null) {
                response.sendRedirect(request.getContextPath() + "/home/viewreservation?action=list");
                return;
            }

            Gift gift = reservation.getGift();

            Reservation.delete(reservation);

            double total = Reservation.getTotalAmountReserved(gift);
            double price = gift.getPrice();

            if (total <= 0) 
            {
                gift.setStatus(be.wishlist.enums.GiftStatus.AVAILABLE);
            } else if (total < price) {
                gift.setStatus(be.wishlist.enums.GiftStatus.PARTIALLY_RESERVED);
            } else {
                gift.setStatus(be.wishlist.enums.GiftStatus.FULLY_RESERVED);
            }

            gift.update();

            response.sendRedirect(request.getContextPath() + "/home/viewreservation?action=list");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home/viewreservation?action=list");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

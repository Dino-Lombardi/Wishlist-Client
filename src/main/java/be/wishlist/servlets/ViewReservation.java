package be.wishlist.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.wishlist.javabeans.Reservation;
import be.wishlist.javabeans.User;


public class ViewReservation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ViewReservation() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("connectedUser") == null) {
                response.sendRedirect(request.getContextPath() + "/home/login");
                return;
            }

            User user = (User) session.getAttribute("connectedUser");

            ArrayList<Reservation> reservations = Reservation.findUserReservations(user.getIdUser());

            request.setAttribute("user", user);
            request.setAttribute("reservations", reservations);

            request.getRequestDispatcher("/WEB-INF/JSP/ViewReservation.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement de vos réservations.");
            request.getRequestDispatcher("/WEB-INF/JSP/Error.jsp").forward(request, response);
        }
    }
}

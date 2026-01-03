package be.wishlist.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.wishlist.enums.InvitationStatus;
import be.wishlist.javabeans.GiftList;
import be.wishlist.javabeans.Invitation;
import be.wishlist.javabeans.User;

public class SendInvitation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SendInvitation() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idGiftlist = Integer.parseInt(request.getParameter("idgiftlist"));
            GiftList giftlist = GiftList.find(idGiftlist);

            if (giftlist == null) {
                request.setAttribute("error", "Liste introuvable.");
                request.getRequestDispatcher("/WEB-INF/JSP/errorPage.jsp").forward(request, response);
                return;
            }

            request.setAttribute("giftlist", giftlist);
            request.getRequestDispatcher("/WEB-INF/JSP/SendInvitation.jsp").forward(request, response);

        } catch (Exception e) 
        {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement de la page d'invitation.");
            request.getRequestDispatcher("/WEB-INF/JSP/errorPage.jsp").forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idGiftlist = Integer.parseInt(request.getParameter("idgiftlist"));
            String username = request.getParameter("username");

            GiftList giftlist = GiftList.find(idGiftlist);
            request.setAttribute("giftlist", giftlist);

            if (username == null || username.trim().isEmpty()) {
                request.setAttribute("error", "Veuillez entrer un nom d'utilisateur.");
                request.getRequestDispatcher("/WEB-INF/JSP/SendInvitation.jsp").forward(request, response);
                return;
            }

            // Vérifier si l'utilisateur existe
            User invitedUser = User.findByUsername(username);

            if (invitedUser == null) {
                request.setAttribute("error", "Utilisateur introuvable.");
                request.getRequestDispatcher("/WEB-INF/JSP/SendInvitation.jsp").forward(request, response);
                return;
            }

            Invitation invitation = new Invitation(InvitationStatus.PENDING, invitedUser,giftlist,LocalDateTime.now());
            boolean ok = Invitation.create(invitation);

            if (!ok) {
                request.setAttribute("error", "Impossible d'envoyer l'invitation.");
            } else {
                request.setAttribute("success", "Invitation envoyée avec succès !");
            }

            request.getRequestDispatcher("/WEB-INF/JSP/SendInvitation.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'envoi de l'invitation.");
            request.getRequestDispatcher("/WEB-INF/JSP/SendInvitation.jsp").forward(request, response);
        }
    }
}

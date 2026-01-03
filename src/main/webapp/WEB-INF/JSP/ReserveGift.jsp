<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="be.wishlist.javabeans.Gift" %>
<%@ page import="be.wishlist.javabeans.Reservation" %>
<%@ page import="java.util.ArrayList" %>

<%
    Gift gift = (Gift) request.getAttribute("gift");
    String contextPath = request.getContextPath();

    // Calcul du montant déjà réservé
    double totalReserved = 0;
    ArrayList<Reservation> reservations = Reservation.findGiftReservations(gift.getIdGift());
    if (reservations != null) {
        for (Reservation r : reservations) {
            totalReserved += r.getAmount();
        }
    }

    double remaining = gift.getPrice() - totalReserved;
    if (remaining < 0) remaining = 0;

    // Détecter si on vient du bouton "Contribuer"
    boolean isGroup = "true".equals(request.getParameter("group"));
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Réserver un cadeau</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>

<body>
    <%@ include file="header.jsp" %>

    <div class="container mt-4">

        <h2 class="mb-4">
            <i class="fas fa-hand-holding-heart"></i>
            <%= isGroup ? "Contribuer" : "Réserver" %> : <%= gift.getName() %>
        </h2>

        <div class="card shadow-sm p-4">

            <% if (gift.getImage() != null && !gift.getImage().trim().isEmpty()) { %>
                <div class="text-center mb-3">
                    <img src="data:image/png;base64,<%= gift.getImage() %>"
                         class="img-fluid"
                         style="max-height: 200px; object-fit: contain;">
                </div>
            <% } %>

            <p><strong>Description :</strong> <%= gift.getDescription() %></p>
            <p><strong>Prix total :</strong> <%= String.format("%.2f", gift.getPrice()) %> €</p>
            <p><strong>Déjà réservé :</strong> <%= String.format("%.2f", totalReserved) %> €</p>
            <p><strong>Reste à financer :</strong>
                <span class="text-warning font-weight-bold">
                    <%= String.format("%.2f", remaining) %> €
                </span>
            </p>

            <hr>

            <form action="<%= contextPath %>/home/reservegift" method="post">

		    <input type="hidden" name="idgift" value="<%= gift.getIdGift() %>">
		    <input type="hidden" name="group" value="true">
		
		    <div class="form-group">
		        <label>Montant que vous souhaitez offrir (€)</label>
		        <input id="amountField"
		               type="number"
		               step="0.01"
		               min="1"
		               max="<%= String.format("%.2f", remaining) %>"
		               name="amount"
		               class="form-control"
		               value="<%= String.format("%.2f", remaining) %>"
		               required>
		    </div>
		
		    <div class="form-group form-check">
		        <input type="checkbox" class="form-check-input" checked disabled>
		        <label class="form-check-label">Achat groupé obligatoire</label>
		    </div>
		
		    <button type="submit" class="btn btn-success btn-block">
		        <i class="fas fa-check"></i> Confirmer la contribution
		    </button>
		
		</form>


        </div>
    </div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="be.wishlist.javabeans.Reservation" %>
<%@ page import="be.wishlist.javabeans.Gift" %>
<%@ page import="be.wishlist.javabeans.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    User user = (User) request.getAttribute("user");
    ArrayList<Reservation> reservations = (ArrayList<Reservation>) request.getAttribute("reservations");

    String contextPath = request.getContextPath();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mes réservations</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>

<body>

<%@ include file="header.jsp" %>

<div class="container mt-4">

    <h2 class="mb-4">
        <i class="fas fa-hand-holding-heart"></i>
        Réservations de <%= user.getFirstname() %> <%= user.getLastname() %>
    </h2>

    <div class="card shadow-sm p-4">

        <% if (reservations == null || reservations.isEmpty()) { %>

            <div class="alert alert-info text-center">
                Vous n'avez effectué aucune réservation pour l’instant.
            </div>

        <% } else { %>

            <table class="table table-bordered table-striped">
                <thead class="thead-light">
                    <tr>
                        <th>Date</th>
                        <th>Cadeau</th>
                        <th>Liste</th>
                        <th>Montant (€)</th>
                        <th>Type</th>
                        <th style="width: 140px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                <% for (Reservation r : reservations) { 
                       Gift g = r.getGift();
                %>
                    <tr>
                        <td><%= r.getReservationDate().format(formatter) %></td>

                        <td>
                            <strong><%= g.getName() %></strong><br>
                            <small class="text-muted"><%= g.getDescription() %></small>
                        </td>

                        <td><%= g.getGiftlist().getTitle() %></td>

                        <td><%= String.format("%.2f", r.getAmount()) %></td>

                        <td>
                            <% if (r.isIsgrouppurchase()) { %>
                                <span class="badge badge-warning">Achat groupé</span>
                            <% } else { %>
                                <span class="badge badge-success">Réservation complète</span>
                            <% } %>
                        </td>

                        <td class="text-center">

                            <a href="<%= contextPath %>/home/deletereservation?id=<%= r.getId() %>"
                               class="btn btn-sm btn-outline-danger">
                                <i class="fas fa-trash">Supprimer</i>
                            </a>

                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>

        <% } %>

        <a href="<%= contextPath %>/home" class="btn btn-outline-secondary mt-3">
            <i class="fas fa-arrow-left"></i> Retour à l'accueil
        </a>

    </div>

</div>

</body>
</html>

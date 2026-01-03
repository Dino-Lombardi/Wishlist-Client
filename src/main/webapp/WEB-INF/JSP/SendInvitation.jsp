
<%@ page import="be.wishlist.javabeans.GiftList" %>

<%
    GiftList giftlist = (GiftList) request.getAttribute("giftlist");
    String contextPath = request.getContextPath();
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Envoyer une invitation</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>

<body>

<%@ include file="header.jsp" %>

<div class="container mt-4">

    <h2 class="mb-4">
        <i class="fas fa-paper-plane"></i>
        Envoyer une invitation pour : <%= giftlist.getTitle() %>
    </h2>

    <div class="card shadow-sm p-4">

        <% if (error != null) { %>
            <div class="alert alert-danger"><%= error %></div>
        <% } %>

        <% if (success != null) { %>
            <div class="alert alert-success"><%= success %></div>
        <% } %>

        <form action="<%= contextPath %>/home/sendinvitation" method="post">

            <input type="hidden" name="idgiftlist" value="<%= giftlist.getIdGiftlist() %>">

            <div class="form-group">
                <label for="username">Nom d'utilisateur à inviter</label>
                <input type="text"
                       class="form-control"
                       id="username"
                       name="username"
                       placeholder="Entrez un nom d'utilisateur valide"
                       required>
            </div>

            <button type="submit" class="btn btn-primary">
                <i class="fas fa-paper-plane"></i> Envoyer l'invitation
            </button>

            <a href="<%= contextPath %>/home/viewgiftlist?id=<%= giftlist.getIdGiftlist() %>"
               class="btn btn-outline-secondary ml-2">
                <i class="fas fa-arrow-left"></i> Retour à la liste
            </a>

        </form>

    </div>

</div>

</body>
</html>

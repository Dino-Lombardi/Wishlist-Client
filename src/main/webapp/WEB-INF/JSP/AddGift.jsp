<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="be.wishlist.javabeans.GiftList" %>

<%
    GiftList gl = (GiftList) request.getAttribute("giftlist");
    if (gl == null) {
%>
    <p>Erreur : aucune GiftList sélectionnée.</p>
<%
        return;
    }
%>

<html>
<head>
    <title>Ajouter un cadeau</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>

<body class="p-4">

<%@ include file="header.jsp" %>

<h2>Ajouter un cadeau à la liste : <%= gl.getTitle() %></h2>

<% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
<% } %>

<form action="<%= request.getContextPath() %>/home/addgift" 
      method="post"
      enctype="multipart/form-data"
      class="mt-4">

    <!-- Nom -->
    <div class="form-group">
        <label>Nom du cadeau</label>
        <input type="text" name="name" class="form-control" required>
    </div>

    <!-- Description -->
    <div class="form-group">
        <label>Description</label>
        <textarea name="description" class="form-control" required></textarea>
    </div>

    <!-- Prix -->
    <div class="form-group">
        <label>Prix (€)</label>
        <input type="number" name="price" step="0.01" class="form-control" required>
    </div>

    <!-- Priorité -->
    <div class="form-group">
        <label>Priorité (1 = haute, 5 = basse)</label>
        <input type="number" name="priority" min="1" max="5" class="form-control" required>
    </div>

    <!-- Lien d'achat -->
    <div class="form-group">
        <label>Lien d'achat</label>
        <input type="url" name="buylink" class="form-control">
    </div>

    <!-- Image -->
    <div class="form-group">
        <label>Image du cadeau</label>
        <input type="file" name="image" accept="image/*" class="form-control" required>
    </div>

    <input type="hidden" name="idgiftlist" value="<%= gl.getIdGiftlist() %>">

    <button type="submit" class="btn btn-primary">Ajouter</button>
</form>

</body>
</html>

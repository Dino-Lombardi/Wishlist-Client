<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="be.wishlist.javabeans.GiftList" %>
<%@ page import="be.wishlist.javabeans.User" %>

<html>
<head>
    <title>GiftLists reçues</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>

<body class="p-4">

<%@ include file="header.jsp" %>

<h2>GiftLists auxquelles vous avez été invité</h2>

<%
    List<GiftList> giftlists = (List<GiftList>) request.getAttribute("giftlist");

    if (giftlists == null || giftlists.isEmpty()) {
%>

    <p>Aucune GiftList reçue.</p>

<%
    } else {
%>

<table class="table table-bordered table-striped mt-3">
    <thead class="thead-light">
        <tr>
            <th>Titre</th>
            <th>Description</th>
            <th>Date de création</th>
            <th>Date d’expiration</th>
            <th>Status</th>
            <th>Propriétaire</th>
            <th>Actions</th>
        </tr>
    </thead>

    <tbody>
<%
        for (GiftList gl : giftlists) {
%>
        <tr>
            <td><%= gl.getTitle() %></td>
            <td><%= gl.getDescription() %></td>
            <td><%= gl.getCreationdate() %></td>
            <td><%= gl.getExpirationDate() %></td>
            <td><%= gl.getStatus() %></td>

            <td>
                <b><%= gl.getOwner().getFirstname() %> <%= gl.getOwner().getLastname() %></b><br>
                <i>@<%= gl.getOwner().getUsername() %></i>
            </td>

            <td>
                <a href="<%= request.getContextPath() %>/home/viewgiftlist?id=<%= gl.getIdGiftlist() %>"
                   class="btn btn-primary btn-sm">
                    Voir la liste
                </a>
            </td>
        </tr>
<%
        } 
%>
    </tbody>
</table>

<%
    } 
%>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="be.wishlist.javabeans.Invitation" %>
<%@ page import="be.wishlist.javabeans.User" %>
<%@ page import="be.wishlist.javabeans.GiftList" %>

<%
    String contextPath = request.getContextPath();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<html>
<head>
    <title>Mes invitations</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="p-4">

<h2>Mes invitations</h2>

<%
    List<Invitation> invitations = (List<Invitation>) request.getAttribute("invitations");

    if (invitations == null || invitations.isEmpty()) {
%>
        <p>Aucune invitation trouvée.</p>
<%
    } else {
%>

<table class="table table-bordered table-striped mt-3">
    <thead class="thead-light">
        <tr>
            <th>Date d’envoi</th>
            <th>Status</th>
            <th>GiftList</th>
            <th>Actions</th>
        </tr>
    </thead>

    <tbody>
<%
        for (Invitation inv : invitations) {
            GiftList gl = inv.getGiftlist();
            String status = inv.getStatus().toString();
            boolean showButtons = !(status.equals("ACCEPTED") || status.equals("REJECTED"));
%>
        <tr>

            <!-- Date d’envoi -->
            <td><%= inv.getSentdate().format(fmt) %></td>

            <!-- Status -->
            <td><%= status %></td>

            <!-- GiftList -->
            <td>
                <% if (gl != null) { %>
                    <b>Titre :</b> <%= gl.getTitle() %><br>
                    <b>Description :</b> <%= gl.getDescription() %><br>
                <% } else { %>
                    <i>Aucune GiftList associée</i>
                <% } %>
            </td>

            <!-- Actions -->
            <td>
                <% if (showButtons) { %>

                    <div class="btn-group btn-group-sm">

                        <!-- Accepter -->
                        <form action="UpdateInvitation" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= inv.getId() %>">
                            <input type="hidden" name="action" value="accept">
                            <button class="btn btn-outline-success">Accepter</button>
                        </form>

                        <!-- Refuser -->
                        <form action="UpdateInvitation" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= inv.getId() %>">
                            <input type="hidden" name="action" value="reject">
                            <button class="btn btn-outline-danger">Refuser</button>
                        </form>

                    </div>

                <% } else { %>

                    <span class="text-muted">
                        Invitation déjà <%= status.toLowerCase() %>.
                    </span>

                <% } %>
            </td>

        </tr>
<%
        } // fin du for
%>
    </tbody>
</table>

<%
    } // fin du else
%>

</body>
</html>

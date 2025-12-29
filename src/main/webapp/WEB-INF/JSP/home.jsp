<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="be.wishlist.javabeans.GiftList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    String contextPath = request.getContextPath();
    List<GiftList> giftlists = (List<GiftList>) request.getAttribute("giftlists");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mes listes de cadeaux</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1>Mes listes de cadeaux</h1>
            <a href="<%= contextPath %>/home/creategiftlist" 
               class="btn btn-primary">
                Nouvelle liste
            </a>
        </div>
        
        <div class="table-responsive">
            <table class="table table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>Titre</th>
                        <th>Description</th>
                        <th>Date de création</th>
                        <th>Date d'expiration</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                    if (giftlists != null && !giftlists.isEmpty()) {
                        for (GiftList giftlist : giftlists) {
                            String formattedCreationDate = "";
                            String formattedExpirationDate = "";
                            
                            if (giftlist.getCreationdate() != null) {
                                formattedCreationDate = giftlist.getCreationdate().format(formatter);
                            }
                            if (giftlist.getExpirationDate() != null) {
                                formattedExpirationDate = giftlist.getExpirationDate().format(formatter);
                            }
                    %>
                        <tr>
                            <td><%= giftlist.getTitle() %></td>
                            <td><%= giftlist.getDescription() %></td>
                            <td><%= formattedCreationDate %></td>
                            <td><%= formattedExpirationDate %></td>
                            <td><%= giftlist.getStatus() %></td>
                            <td>
                                <div class="btn-group btn-group-sm">
                                    <a href="<%= contextPath %>/home/viewgiftlist?id=<%= giftlist.getIdGiftlist() %>" 
                                       class="btn btn-outline-info">Voir
                                    </a>
                                    <a href="<%= contextPath %>/home/editgiftlist?id=<%= giftlist.getIdGiftlist() %>" 
                                       class="btn btn-outline-warning">Modifier
                                    </a>
                                    <a href="<%= contextPath %>/home/deletegiftlist?id=<%= giftlist.getIdGiftlist() %>" 
                                       class="btn btn-outline-danger" 
                                       onclick="return confirm('Supprimer cette liste ?');">Supprimer
                                    </a>
                                </div>
                            </td>
                        </tr>
                    <% 
                        }
                    } else { 
                    %>
                        <tr>
                            <td colspan="6" class="text-center">
                                Aucune liste de cadeaux
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
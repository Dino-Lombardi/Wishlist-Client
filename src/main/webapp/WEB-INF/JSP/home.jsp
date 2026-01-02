<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="be.wishlist.javabeans.GiftList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    List<GiftList> giftlists = (List<GiftList>) request.getAttribute("giftlists");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String contextPath = request.getContextPath();
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
    <%@ include file="header.jsp" %>
    
    <div class="container mt-4">
        <!-- Messages de succès/erreur -->
        <% if (session.getAttribute("successMessage") != null) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= session.getAttribute("successMessage") %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <% session.removeAttribute("successMessage"); %>
        <% } %>
        
        <% if (session.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= session.getAttribute("errorMessage") %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <% session.removeAttribute("errorMessage"); %>
        <% } %>
        
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
                            <td>
                                <% 
                                    String description = giftlist.getDescription();
                                    if (description != null && !description.trim().isEmpty()) { %>
                                       
										<%= description %>
                                   	<% } %>
                            </td>
                            <td><%= formattedCreationDate %></td>
                            <td><%= formattedExpirationDate %></td>
                            <td>
                                <span class="badge badge-<%= 
                                    giftlist.getStatus().toString().equals("ACTIVE") ? "success" :
                                    giftlist.getStatus().toString().equals("INACTIVE") ? "warning" :
                                    giftlist.getStatus().toString().equals("EXPIRED") ? "danger" :
                                    "warning" %>">
                                    <%= giftlist.getStatus() %>
                                </span>
                            </td>
                            <td>
                                <div class="btn-group btn-group-sm">
                                    <a href="<%= contextPath %>/home/viewgiftlist?id=<%= giftlist.getIdGiftlist() %>" 
                                       class="btn btn-outline-info">Voir
                                    </a>
                                    <a href="<%= contextPath %>/home/editgiftlist?id=<%= giftlist.getIdGiftlist() %>" 
                                       class="btn btn-outline-warning">Modifier
                                    </a>
                                    <!-- Formulaire POST pour suppression -->
                                    <form action="<%= contextPath %>/home/deletegiftlist" method="POST" style="display: inline;">
                                        <input type="hidden" name="id" value="<%= giftlist.getIdGiftlist() %>">
                                        <button type="submit" class="btn btn-outline-danger" onclick= "return confirm('Supprimer cette liste ?');">
                                        	Supprimer
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    <% 
                        }
                    } else { 
                    %>
                        <tr>
                            <td colspan="6" class="text-center py-4">
                                <div class="alert alert-info">
                                    <h5>Vous n'avez pas encore de liste de cadeaux</h5>
                                    <p class="mb-0">Créez votre première liste en cliquant sur "Nouvelle liste".</p>
                                </div>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
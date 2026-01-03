<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="be.wishlist.javabeans.GiftList" %>
<%@ page import="be.wishlist.javabeans.Gift" %>
<%@ page import="be.wishlist.enums.GiftStatus" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.ArrayList" %>
<%
    GiftList giftlist = (GiftList) request.getAttribute("giftlist");
    ArrayList<Gift> gifts = giftlist != null ? giftlist.getGifts() : null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%= giftlist != null ? giftlist.getTitle() : "Liste de cadeaux" %></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="container mt-4">
        <% if (giftlist != null) { %>
        <div class="card mb-4">
            <div class="card-header bg-info text-white">
                <div class="d-flex justify-content-between align-items-center">
                    <h2 class="mb-0">
                        <i class="fas fa-gift"></i> <%= giftlist.getTitle() %>
                    </h2>
                    <span class="badge badge-light">
                        <%= giftlist.getStatus() %>
                    </span>
                </div>
            </div>
            <div class="card-body">
                <% if (giftlist.getDescription() != null && !giftlist.getDescription().trim().isEmpty()) { %>
                    <p class="card-text"><%= giftlist.getDescription() %></p>
                <% } %>
                
                <div class="row mt-3">
                    <div class="col-md-6">
                        <p><strong>Créée le :</strong> 
                            <% if (giftlist.getCreationdate() != null) { %>
                                <%= giftlist.getCreationdate().format(formatter) %>
                            <% } %>
                        </p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Expire le :</strong> 
                            <% if (giftlist.getExpirationDate() != null) { %>
                                <%= giftlist.getExpirationDate().format(formatter) %>
                            <% } %>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
        
        <!-- Section des cadeaux -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3>Cadeaux</h3>
            <a href="<%= contextPath %>/home/creategift?id=<%= giftlist != null ? giftlist.getIdGiftlist() : "" %>" 
               class="btn btn-primary">
                <i class="fas fa-plus"></i> Ajouter un cadeau
            </a>
        </div>
        
        <% 
        if (gifts != null && !gifts.isEmpty()) { %>
            <div class="row">
            <% for (Gift gift : gifts) { 
                String statusClass = "";
                String statusText = "";
                
                switch(gift.getStatus()) {
                    case AVAILABLE: 
                        statusClass = "success"; 
                        statusText = "Disponible";
                        break;
                    case FULLY_RESERVED: 
                        statusClass = "danger"; 
                        statusText = "Complètement réservé";
                        break;
                    case PARTIALLY_RESERVED: 
                        statusClass = "warning"; 
                        statusText = "Partiellement réservé";
                        break;
                }
            %>
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100 shadow-sm">
                        <% if (gift.getImage() != null && !gift.getImage().trim().isEmpty()) { %>
                            <div class="text-center mt-3">
                                <img src="<%= gift.getImage() %>" alt="<%= gift.getName() %>" 
                                     class="card-img-top" style="max-height: 150px; width: auto;">
                            </div>
                        <% } %>
                        
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start mb-2">
                                <h5 class="card-title mb-0"><%= gift.getName() %></h5>
                                <span class="badge badge-<%= statusClass %>">
                                    <%= statusText %>
                                </span>
                            </div>
                            
                            <div class="mb-2">
                                <% 
                                int priority = gift.getPriority();
                                for (int i = 1; i <= 5; i++) { 
                                    if (i <= priority) {
                                %>
                                    <i class="fas fa-star text-warning"></i>
                                <% } else { %>
                                    <i class="far fa-star text-muted"></i>
                                <% } 
                                } %>
                                <small class="text-muted ml-2">Priorité <%= priority %></small>
                            </div>
                            
                            <% if (gift.getDescription() != null && !gift.getDescription().trim().isEmpty()) { %>
                                <p class="card-text mb-3"><%= gift.getDescription() %></p>
                            <% } %>
                            
                            <div class="mb-3">
                                <span class="h5 text-success font-weight-bold"><%= String.format("%.2f", gift.getPrice()) %> €</span>
                            </div>
                        </div>
                        
                        <div class="card-footer bg-white">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <% if (gift.getBuylink() != null && !gift.getBuylink().trim().isEmpty()) { %>
                                        <a href="<%= gift.getBuylink() %>" target="_blank" 
                                           class="btn btn-sm btn-outline-primary">
                                            <i class="fas fa-shopping-cart"></i> Acheter
                                        </a>
                                    <% } %>
                                </div>
                                
                                <div class="btn-group">
                                    <% if (gift.canBeModifiedOrDeleted()) { %>
                                        <a href="<%= contextPath %>/home/editgift?id=<%= gift.getIdGift() %>" 
                                           class="btn btn-sm btn-outline-warning">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="<%= contextPath %>/home/deletegift?id=<%= gift.getIdGift() %>" 
                                           class="btn btn-sm btn-outline-danger">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    <% } else { %>
                                        <span class="btn btn-sm btn-outline-warning disabled" 
                                              title="Ce cadeau ne peut pas être modifié car il est réservé">
                                            <i class="fas fa-edit"></i>
                                        </span>
                                        <span class="btn btn-sm btn-outline-danger disabled"
                                              title="Ce cadeau ne peut pas être supprimé car il est réservé">
                                            <i class="fas fa-trash"></i>
                                        </span>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
            </div>
        <% } else { %>
            <div class="alert alert-info text-center">
                <h4><i class="fas fa-gift"></i> Aucun cadeau pour l'instant</h4>
                <p>Commencez par ajouter des cadeaux à votre liste !</p>
            </div>
        <% } %>
        
        <!-- Boutons de navigation -->
        <div class="mt-4 d-flex justify-content-between">
            <a href="<%= contextPath %>/home" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left"></i> Retour aux listes
            </a>
        </div>
    </div>
</body>
</html>
<%@ page import="be.wishlist.javabeans.GiftList, be.wishlist.enums.GiftListStatus, 
                 java.time.LocalDate, java.time.format.DateTimeFormatter, 
                 java.util.Map" %>
<%
    Map<String, String> messages = (Map<String, String>) request.getAttribute("messages");
    
    // Récupérer les valeurs soumises en cas d'erreur
    String submittedTitle = (String) request.getAttribute("submittedTitle");
    String submittedDescription = (String) request.getAttribute("submittedDescription");
    String submittedExpirationDate = (String) request.getAttribute("submittedExpirationDate");
    String submittedStatus = (String) request.getAttribute("submittedStatus");
    
    String title = submittedTitle != null ? submittedTitle : "";
    String description = submittedDescription != null ? submittedDescription : "";
    String expirationdate = submittedExpirationDate != null ? submittedExpirationDate : "";
    String status = submittedStatus != null ? submittedStatus : "ACTIVE";
    
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Créer une liste de cadeaux</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <%@include file="header.jsp" %>
    
    <div class="container">
        <div class="row justify-content-center mt-4">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header text-center">
                        <h4>Créer une nouvelle liste de cadeaux</h4>
                    </div>
                    <div class="card-body">
                        <form action="<%= contextPath %>/home/creategiftlist" method="POST">
                            
                            <!-- Titre -->
                            <div class="form-group">
                                <label for="title">Titre <span class="text-danger">*</span></label>
                                <input type="text" 
                                       class="form-control <%= messages != null && messages.containsKey("title") ? "is-invalid" : "" %>" 
                                       id="title" 
                                       name="title" 
                                       value="<%= title %>" 
                                       required
                                       maxlength="50">
                                <% if (messages != null && messages.containsKey("title")) { %>
                                    <div class="invalid-feedback"><%= messages.get("title") %></div>
                                <% } else { %>
                                    <small class="form-text text-muted">50 caractères maximum</small>
                                <% } %>
                            </div>
                            
                            <!-- Description -->
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea class="form-control <%= messages != null && messages.containsKey("description") ? "is-invalid" : "" %>" 
                                          id="description" 
                                          name="description" 
                                          rows="3"
                                          maxlength="500"><%= description %></textarea>
                                <% if (messages != null && messages.containsKey("description")) { %>
                                    <div class="invalid-feedback"><%= messages.get("description") %></div>
                                <% } else { %>
                                    <small class="form-text text-muted">500 caractères maximum</small>
                                <% } %>
                            </div>
                            
                            <!-- Date d'expiration -->
                            <div class="form-group">
                                <label for="expirationdate">Date d'expiration <span class="text-danger">*</span></label>
                                <input type="date" 
                                       class="form-control <%= messages != null && messages.containsKey("expirationdate") ? "is-invalid" : "" %>" 
                                       id="expirationdate" 
                                       name="expirationdate" 
                                       value="<%= expirationdate %>" 
                                       required
                                       min="<%= LocalDate.now().plusDays(1) %>">
                                <% if (messages != null && messages.containsKey("expirationdate")) { %>
                                    <div class="invalid-feedback d-block"><%= messages.get("expirationdate") %></div>
                                <% } else { %>
                                    <small class="form-text text-muted">Sélectionnez une date future</small>
                                <% } %>
                            </div>
                            
                            <!-- Statut (optionnel, par défaut ACTIVE) -->
                            <div class="form-group">
                                <label for="status">Statut</label>
                                <select class="form-control <%= messages != null && messages.containsKey("status") ? "is-invalid" : "" %>" 
                                        id="status" 
                                        name="status">
                                    <% for (GiftListStatus giftStatus : GiftListStatus.values()) { 
                                        if (!giftStatus.toString().equals("EXPIRED")) { %>
                                        <option value="<%= giftStatus.toString() %>" 
                                                <%= status.equals(giftStatus.toString()) ? "selected" : "" %>>
                                            <%= giftStatus.toString() %>
                                        </option>
                                    <% } } %>
                                </select>
                                <% if (messages != null && messages.containsKey("status")) { %>
                                    <div class="invalid-feedback"><%= messages.get("status") %></div>
                                <% } else { %>
                                    <small class="form-text text-muted">Par défaut : ACTIVE</small>
                                <% } %>
                            </div>
                            
                            <!-- Informations qui seront générées automatiquement -->
                            <div class="border rounded p-3 mb-4 bg-light">
                                <h6 class="text-muted mb-3">Informations générées automatiquement</h6>
                                
                                <div class="alert alert-info">
                                    <p class="mb-2"><strong>Date de création :</strong> Sera automatiquement définie à aujourd'hui.</p>
                                    <p class="mb-0"><strong>Lien de partage :</strong> Un lien unique sera généré automatiquement.</p>
                                </div>
                            </div>
                            
                            <!-- Messages d'erreur -->
                            <% if (messages != null && messages.containsKey("error")) { %>
                                <div class="alert alert-danger" role="alert"><%= messages.get("error") %></div>
                            <% } %>
                            
                            <!-- Boutons -->
                            <div class="d-flex justify-content-between mt-4">
                                <a href="<%= contextPath %>/home" class="btn btn-outline-secondary">Retour</a>
                                <button type="submit" class="btn btn-success">Créer la liste</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
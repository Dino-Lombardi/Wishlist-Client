<%@ page import="be.wishlist.javabeans.GiftList, be.wishlist.enums.GiftListStatus, 
                 java.time.LocalDate, java.time.format.DateTimeFormatter, 
                 java.util.Map, java.util.Locale" %>
<%
    GiftList giftlist = (GiftList) request.getAttribute("giftlist");
    Map<String, String> messages = (Map<String, String>) request.getAttribute("messages");
    
    // Format YYYY-MM-DD pour HTML5 date picker
    String expirationdatestr = "";
    if (giftlist != null && giftlist.getExpirationDate() != null) {
    	expirationdatestr = giftlist.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    String contextPath = request.getContextPath();
    Locale frenchLocale = Locale.FRENCH;
    DateTimeFormatter frenchFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", frenchLocale);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modifier la liste de cadeaux</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <%@include file="header.jsp" %>
    
    <div class="container">
        <div class="row justify-content-center mt-4">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header text-center">
                        <h4>Modifier la liste de cadeaux</h4>
                    </div>
                    <div class="card-body">
                        <form action="<%= contextPath %>/home/editgiftlist" method="POST">
                            <input type="hidden" name="id" value="<%= giftlist != null ? giftlist.getIdGiftlist() : "" %>">
                            
                            <!-- Titre -->
                            <div class="form-group">
                                <label for="title">Titre <span class="text-danger">*</span></label>
                                <input type="text" 
                                       class="form-control <%= messages != null && messages.containsKey("title") ? "is-invalid" : "" %>" 
                                       id="title" 
                                       name="title" 
                                       value="<%= giftlist != null ? giftlist.getTitle() : "" %>" 
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
                                          maxlength="500"><%= giftlist != null && giftlist.getDescription() != null ? giftlist.getDescription() : "" %></textarea>
                                <% if (messages != null && messages.containsKey("description")) { %>
                                    <div class="invalid-feedback"><%= messages.get("description") %></div>
                                <% } else { %>
                                    <small class="form-text text-muted">500 caractères maximum</small>
                                <% } %>
                            </div>
                            
                            <!-- Date d'expiration avec calendrier HTML5 -->
                            <div class="form-group">
                                <label for="expirationdate">Date d'expiration <span class="text-danger">*</span></label>
                                <input type="date" 
                                       class="form-control <%= messages != null && messages.containsKey("expirationdate") ? "is-invalid" : "" %>" 
                                       id="expirationdate" 
                                       name="expirationdate" 
                                       value="<%= expirationdatestr %>" 
                                       required
                                       min="<%= LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) %>">
                                <% if (messages != null && messages.containsKey("expirationdate")) { %>
                                    <div class="invalid-feedback d-block"><%= messages.get("expirationdate") %></div>
                                <% } else { %>
                                    <small class="form-text text-muted">Sélectionnez une date future dans le calendrier</small>
                                <% } %>
                            </div>
                            
                            <!-- Statut -->
                            <div class="form-group">
                                <label for="status">Statut <span class="text-danger">*</span></label>
                                <select class="form-control <%= messages != null && messages.containsKey("status") ? "is-invalid" : "" %>" 
                                        id="status" 
                                        name="status"
                                        required>
                                    <% for (GiftListStatus giftStatus : GiftListStatus.values()) { 
                                        if (!giftStatus.toString().equals("EXPIRED")) { %>
                                        <option value="<%= giftStatus.toString() %>" 
                                                <%= giftlist != null && giftlist.getStatus() == giftStatus ? "selected" : "" %>>
                                            <%= giftStatus.toString() %>
                                        </option>
                                    <% } } %>
                                </select>
                                <% if (messages != null && messages.containsKey("status")) { %>
                                    <div class="invalid-feedback"><%= messages.get("status") %></div>
                                <% } %>
                            </div>
                            
                            <!-- Informations non modifiables -->
                            <div class="border rounded p-3 mb-4 bg-light">
                                <h6 class="text-muted mb-3">Informations non modifiables</h6>
                                
                                <!-- Date de création -->
                                <div class="form-group mb-3">
                                    <label class="text-muted">Date de création</label>
                                    <div class="form-control-plaintext border bg-white p-2">
                                        <%= giftlist != null && giftlist.getCreationdate() != null ? 
                                            giftlist.getCreationdate().format(frenchFormatter) : "Non définie" %>
                                    </div>
                                </div>
                                
                                <!-- Lien de partage -->
                                <div class="form-group">
                                    <label class="text-muted">Lien de partage</label>
                                    <div class="form-control-plaintext border bg-white p-2">
                                        <% if (giftlist != null && giftlist.getSharelink() != null && !giftlist.getSharelink().isEmpty()) { %>
                                            <a href="<%= giftlist.getSharelink() %>" target="_blank" class="text-primary">
                                                <%= giftlist.getSharelink() %>
                                            </a>
                                        <% } else { %>
                                            <span class="text-muted">Aucun lien de partage généré</span>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Messages -->
                            <% if (messages != null && messages.containsKey("error")) { %>
                                <div class="alert alert-danger" role="alert"><%= messages.get("error") %></div>
                            <% } %>
                            <% if (session.getAttribute("successMessage") != null) { %>
                                <div class="alert alert-success" role="alert">
                                    <%= session.getAttribute("successMessage") %>
                                    <% session.removeAttribute("successMessage"); %>
                                </div>
                            <% } %>
                            
                            <!-- Boutons -->
                            <div class="d-flex justify-content-between mt-4">
                                <a href="<%= contextPath %>/home" class="btn btn-outline-secondary">Retour</a>
                                <button type="submit" class="btn btn-primary">Mettre à jour</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
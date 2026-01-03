<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="be.wishlist.javabeans.Gift" %>
<%@ page import="be.wishlist.enums.GiftStatus" %>

<%
    Gift gift = (Gift) request.getAttribute("gift");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier le cadeau</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>

<body>
    <%@ include file="header.jsp" %>

    <div class="container mt-4">

        <h2 class="mb-4">
            <i class="fas fa-edit"></i> Modifier le cadeau : <%= gift.getName() %>
        </h2>

        <form action="<%= contextPath %>/home/updategift" method="post" enctype="multipart/form-data" class="card p-4 shadow-sm">

            <input type="hidden" name="idgift" value="<%= gift.getIdGift() %>">

            <div class="form-group">
                <label>Nom du cadeau</label>
                <input type="text" name="name" class="form-control" value="<%= gift.getName() %>" required>
            </div>

            <div class="form-group">
                <label>Description</label>
                <textarea name="description" class="form-control" rows="3"><%= gift.getDescription() %></textarea>
            </div>

            <div class="form-group">
                <label>Prix (€)</label>
                <input type="number" step="0.01" name="price" class="form-control" value="<%= gift.getPrice() %>" required>
            </div>

            <div class="form-group">
                <label>Priorité</label>
                <select name="priority" class="form-control">
                    <% for (int i = 1; i <= 5; i++) { %>
                        <option value="<%= i %>" <%= (gift.getPriority() == i ? "selected" : "") %>>
                            <%= i %>
                        </option>
                    <% } %>
                </select>
            </div>

            <div class="form-group">
                <label>Statut</label>
                <input type="text" class="form-control" value="<%= gift.getStatus() %>" readonly>
            </div>

            <div class="form-group">
                <label>Lien d'achat</label>
                <input type="text" name="buylink" class="form-control" value="<%= gift.getBuylink() %>">
            </div>

            <% if (gift.getImage() != null && !gift.getImage().trim().isEmpty()) { %>
                <div class="form-group">
                    <label>Image actuelle</label><br>
                    <img src="data:image/png;base64,<%= gift.getImage() %>" 
                         style="max-width: 200px; max-height: 200px;" class="img-thumbnail">
                </div>
            <% } %>

            <div class="form-group">
                <label>Changer l'image</label>
                <input type="file" name="image" class="form-control-file">
            </div>

            <div class="d-flex justify-content-between mt-4">
                <a href="<%= contextPath %>/home/viewgiftlist?id=<%= gift.getGiftlist().getIdGiftlist() %>" 
                   class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Retour
                </a>

               <button type="submit" class="btn btn-primary"> <i class="fas fa-save"></i> 
               Mettre à jour 
               </button>
            </div>

        </form>
    </div>

</body>
</html>

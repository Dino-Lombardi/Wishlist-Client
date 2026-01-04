<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String error = (String) request.getAttribute("error");
    String successMessage = (String) request.getAttribute("successMessage");
    String usernameParam = request.getParameter("username");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion</title>
<!-- Bootstrap 4 CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container">
        <div class="row justify-content-center mt-5">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header text-center">
                        <h4>Connexion</h4>
                    </div>
                    <div class="card-body">
                        <form action="login" method="POST">
                            <% if(error != null) { %>
                                <div class="alert alert-danger" role="alert">
                                    <%= error %>
                                </div>
                            <% } else if(successMessage != null) { %>
                                <div class="alert alert-success" role="alert">
                                    <%= successMessage %>
                                </div>
                            <% } %>
                            
                            <div class="form-group">
                                <label for="username">Nom d'utilisateur</label>
                                <input type="text" 
                                       class="form-control" 
                                       id="username" 
                                       name="username" 
                                       value="<%= usernameParam != null ? usernameParam : "" %>" 
                                       required>
                            </div>
                            
                            <div class="form-group">
                                <label for="password">Mot de passe</label>	
                                <input type="password" 
                                       class="form-control" 
                                       id="password" 
                                       name="password" 
                                       required>
                            </div>
                            
                            <button type="submit" class="btn btn-primary btn-block mb-3">Connexion</button>
                        </form>
                        
                        <div class="text-center mt-3 pt-3 border-top">
                            <p class="text-muted mb-2">Nouveau sur notre site ?</p>
                            <a href="register" class="btn btn-success btn-block">Créer un compte</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
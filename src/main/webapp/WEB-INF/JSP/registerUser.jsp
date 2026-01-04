<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%
    Map<String, String> messages = (Map<String, String>) request.getAttribute("messages");
    
    String firstnameParam = request.getParameter("firstname");
    String lastnameParam = request.getParameter("lastname");
    String usernameParam = request.getParameter("username");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
<!-- Bootstrap 4 CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container">
        <div class="row justify-content-center mt-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header text-center">
                        <h4>Créer un compte</h4>
                    </div>
                    <div class="card-body">
                        <form action="register" method="POST">
                            <div class="form-group">
                                <label for="firstname">Prénom</label>
                                <input type="text" 
                                       class="form-control <%= messages != null && messages.containsKey("firstname") ? "is-invalid" : "" %>" 
                                       id="firstname" 
                                       name="firstname" 
                                       value="<%= firstnameParam != null ? firstnameParam : "" %>" 
                                       required>
                                <% if (messages != null && messages.containsKey("firstname")) { %>
                                    <div class="invalid-feedback">
                                        <%= messages.get("firstname") %>
                                    </div>
                                <% } %>
                            </div>
                            
                            <div class="form-group">
                                <label for="lastname">Nom de famille</label>
                                <input type="text" 
                                       class="form-control <%= messages != null && messages.containsKey("lastname") ? "is-invalid" : "" %>" 
                                       id="lastname" 
                                       name="lastname" 
                                       value="<%= lastnameParam != null ? lastnameParam : "" %>" 
                                       required>
                                <% if (messages != null && messages.containsKey("lastname")) { %>
                                    <div class="invalid-feedback">
                                        <%= messages.get("lastname") %>
                                    </div>
                                <% } %>
                            </div>
                            
                            <div class="form-group">
                                <label for="username">Nom d'utilisateur</label>
                                <input type="text" 
                                       class="form-control <%= messages != null && messages.containsKey("username") ? "is-invalid" : "" %>" 
                                       id="username" 
                                       name="username" 
                                       value="<%= usernameParam != null ? usernameParam : "" %>" 
                                       required>
                                <% if (messages != null && messages.containsKey("username")) { %>
                                    <div class="invalid-feedback">
                                        <%= messages.get("username") %>
                                    </div>
                                <% } %>
                            </div>
                            
                            <div class="form-group">
                                <label for="password">Mot de passe</label>
                                <input type="password" 
                                       class="form-control <%= messages != null && messages.containsKey("password") ? "is-invalid" : "" %>" 
                                       id="password" 
                                       name="password" 
                                       required>
                                <% if (messages != null && messages.containsKey("password")) { %>
                                    <div class="invalid-feedback">
                                        <%= messages.get("password") %>
                                    </div>
                                <% } %>
                            </div>
                            
                            <div class="form-group">
                                <label for="confirmPassword">Confirmer le mot de passe</label>
                                <input type="password" 
                                       class="form-control <%= messages != null && messages.containsKey("confirmPassword") ? "is-invalid" : "" %>" 
                                       id="confirmPassword" 
                                       name="confirmPassword" 
                                       required>
                                <% if (messages != null && messages.containsKey("confirmPassword")) { %>
                                    <div class="invalid-feedback">
                                        <%= messages.get("confirmPassword") %>
                                    </div>
                                <% } %>
                            </div>
                            
                            <% if (messages != null && messages.containsKey("error")) { %>
                                <div class="alert alert-danger" role="alert">
                                    <%= messages.get("error") %>
                                </div>
                            <% } %>
                            
                            <button type="submit" class="btn btn-primary btn-block mb-3">
                                Créer le compte
                            </button>
                        </form>
                        
                        <div class="text-center mt-3 pt-3 border-top">
                            <p class="text-muted mb-2">Déjà un compte ?</p>
                            <a href="login" class="btn btn-outline-primary btn-block">Se connecter</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                            <!-- Prénom -->
                            <div class="form-group">
                                <label for="firstname">Prénom</label>
                                <input type="text" 
                                       class="form-control ${not empty messages.firstname ? 'is-invalid' : ''}" 
                                       id="firstname" 
                                       name="firstname" 
                                       value="${param.firstname}" 
                                       required>
                                ${not empty messages.firstname ? 
                                    '<div class="invalid-feedback">' += messages.firstname += '</div>' : ''}
                            </div>
                            
                            <!-- Nom de famille -->
                            <div class="form-group">
                                <label for="lastname">Nom de famille</label>
                                <input type="text" 
                                       class="form-control ${not empty messages.lastname ? 'is-invalid' : ''}" 
                                       id="lastname" 
                                       name="lastname" 
                                       value="${param.lastname}" 
                                       required>
                                ${not empty messages.lastname ? 
                                    '<div class="invalid-feedback">' += messages.lastname += '</div>' : ''}
                            </div>
                            
                            <!-- Nom d'utilisateur -->
                            <div class="form-group">
                                <label for="username">Nom d'utilisateur</label>
                                <input type="text" 
                                       class="form-control ${not empty messages.username ? 'is-invalid' : ''}" 
                                       id="username" 
                                       name="username" 
                                       value="${param.username}" 
                                       required>
                                ${not empty messages.username ? 
                                    '<div class="invalid-feedback">' += messages.username += '</div>' : ''}
                            </div>
                            
                            <!-- Mot de passe -->
                            <div class="form-group">
                                <label for="password">Mot de passe</label>
                                <input type="password" 
                                       class="form-control ${not empty messages.password ? 'is-invalid' : ''}" 
                                       id="password" 
                                       name="password" 
                                       required>
                                ${not empty messages.password ? 
                                    '<div class="invalid-feedback">' += messages.password += '</div>' : ''}
                            </div>
                            
                            <!-- Confirmation du mot de passe -->
                            <div class="form-group">
                                <label for="confirmPassword">Confirmer le mot de passe</label>
                                <input type="password" 
                                       class="form-control ${not empty messages.confirmPassword ? 'is-invalid' : ''}" 
                                       id="confirmPassword" 
                                       name="confirmPassword" 
                                       required>
                                ${not empty messages.confirmPassword ? 
                                    '<div class="invalid-feedback">' += messages.confirmPassword += '</div>' : ''}
                            </div>
                            
                            <!-- Bouton de soumission -->
                            <button type="submit" class="btn btn-primary btn-block mb-3">
                                Créer le compte
                            </button>
                        </form>
                        
                        <!-- Bouton de connexion -->
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
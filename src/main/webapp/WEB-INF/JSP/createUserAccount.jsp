<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
</head>
<body>

	<form action="Inscription" method="POST">
		<h1>Créer un compte</h1>
		<p>
            <label for="firstName">Prénom:</label>
            <input type="text" id="firstname" name="firstname" value=${param.firstname} required><br><br>
            <span class = "error">${messages.firstname }</span>
        </p>
        
        <p>
            <label for="lastName">Nom de famille:</label>
            <input type="text" id="lastname" name="lastname" value=${param.lastname} required><br><br>
            <span class = "error">${messages.lastname}</span>
        </p>
		
		<p>
	        <label for="username">Nom d'utilisateur:</label>
	        <input type="text" id="username" name="username" value=${param.username} required><br><br>
	        <span class = "error">${messages.username}</span>
        </p>
        
        <p>
	        <label for="password">Mot de passe:</label>
	        <input type="password" id="password" name="password" required><br><br>
			<span class = "error">${messages.password}</span>
		</p>
		
		<p>
            <label for="confirmPassword">Confirmer le mot de passe:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>
            <span class = "error">${messages.confirmPassword}</span>
        
        <p>
	        <input type="submit" value="Create Account">
	        <span class = "success">${messages.success}</span>
        </p>
    </form>
</body>
</html>
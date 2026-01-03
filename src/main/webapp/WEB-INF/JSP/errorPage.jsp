<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Erreur serveur</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>

<body class="p-4">

<%@ include file="header.jsp" %>

<h2 class="text-danger">Une erreur est survenue</h2>

<div class="alert alert-danger mt-3">
    <strong>Message :</strong>
    <pre><%= exception != null ? exception.getMessage() : "Aucun message disponible" %></pre>
</div>

<div class="alert alert-warning mt-3">
    <strong>Stacktrace :</strong>
    <pre>
<%
    if (exception != null) {
        exception.printStackTrace(new java.io.PrintWriter(out));
    } else {
        out.println("Aucune exception disponible.");
    }
%>
    </pre>
</div>

</body>
</html>

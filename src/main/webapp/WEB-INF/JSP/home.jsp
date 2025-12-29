<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mes listes de cadeaux</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1>Mes listes de cadeaux</h1>
            <a href="${pageContext.request.contextPath}/home/creategiftlist" 
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
                    <c:forEach var="giftlist" items="${giftlists}">
                        <fmt:parseDate var="dateCreation" value="${giftlist.creationdate}" pattern="yyyy-MM-dd"/>
                        <fmt:parseDate var="dateExp" value="${giftlist.expirationdate}" pattern="yyyy-MM-dd"/>
                        <tr>
                            <td>${giftlist.title}</td>
                            <td>${giftlist.description}</td>
                            <td><fmt:formatDate value="${dateCreation}" pattern="dd/MM/yyyy"/></td>
                            <td><fmt:formatDate value="${dateExp}" pattern="dd/MM/yyyy"/></td>
                            <td>${giftlist.status}</td>
                            <td>
                                <div class="btn-group btn-group-sm">
                                    <a href="${pageContext.request.contextPath}/home/viewgiftlist?id=${giftlist.idgiftlist}" 
                                       class="btn btn-outline-info">Voir
                                    </a>
                                    <a href="${pageContext.request.contextPath}/home/editgiftlist?id=${giftlist.idgiftlist}" 
                                       class="btn btn-outline-warning">Modifier
                                    </a>
                                    <a href="${pageContext.request.contextPath}/home/deletegiftlist?id=${giftlist.idgiftlist}" 
                                       class="btn btn-outline-danger" 
                                       onclick="return confirm('Supprimer cette liste ?');">Supprimer
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    
                    <c:if test="${empty giftlists}">
                        <tr>
                            <td colspan="6" class="text-center">
                                Aucune liste de cadeaux
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
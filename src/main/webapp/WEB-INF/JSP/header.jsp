<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="be.wishlist.javabeans.User" %>
<%
    String contextPath = request.getContextPath();
    User connectedUser = (User) session.getAttribute("connectedUser");
%>
<div style="background: #f8f9fa; border-bottom: 1px solid #ddd; padding: 10px 0;">
    <div style="max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; padding: 0 20px;">
        <!-- Logo et navigation -->
        <div>
            <a href="<%= contextPath %>/home" style="text-decoration: none; color: #333; font-weight: bold; margin-right: 20px;">
                🎁 Wishlist
            </a>
            
            <% if (connectedUser != null) { %>
                <a href="<%= contextPath %>/home" style="margin-right: 15px; color: #007bff; text-decoration: none;">
                    Mes Listes
                </a>
                <a href="<%= contextPath %>/invitations" style="margin-right: 15px; color: #007bff; text-decoration: none;">
                    Invitations
                </a>
                <a href="<%= contextPath %>/participations" style="margin-right: 15px; color: #007bff; text-decoration: none;">
                    Participations
                </a>
            <% } %>
        </div>
        
        <!-- Utilisateur et déconnexion -->
        <% if (connectedUser != null) { %>
            <div>
                <span style="margin-right: 15px;"><%= connectedUser.getFirstname() %></span>
                <form action="<%= contextPath %>/logout" method="post" style="display: inline;">
                    <button type="submit" style="background: #dc3545; color: white; border: none; padding: 5px 10px; border-radius: 3px; cursor: pointer;">
                        Déconnexion
                    </button>
                </form>
            </div>
        <% } %>
    </div>
</div>
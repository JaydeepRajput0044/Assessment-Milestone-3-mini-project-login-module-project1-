<%@ page session="true" %>
<%
    com.app.model.User u = (com.app.model.User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect("login");
        return;
    }
%>
<h2>Welcome ${user.username}!</h2>
<a href="logout">Logout</a>

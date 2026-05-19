<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users | TravelBooking Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="sidebar.jsp"><jsp:param name="active" value="users"/></jsp:include>

    <main class="admin-main">
        <div class="admin-header">
            <h1>Users</h1>
            <span class="badge badge-primary">${users.size()} total</span>
        </div>

        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Joined</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${users}">
                        <tr>
                            <td><strong>${u.fullName}</strong></td>
                            <td>${u.email}</td>
                            <td>${u.phone != null ? u.phone : '-'}</td>
                            <td><fmt:formatDate value="${u.createdAt}" pattern="MMM dd, yyyy"/></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.idString}"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Delete this user? This cannot be undone.');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${empty users}">
            <div class="empty-state">
                <div class="empty-icon">👥</div>
                <h4>No users yet</h4>
            </div>
        </c:if>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Hotels | TravelBooking Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="sidebar.jsp"><jsp:param name="active" value="hotels"/></jsp:include>

    <main class="admin-main">
        <div class="admin-header">
            <h1>Hotels</h1>
            <a href="${pageContext.request.contextPath}/admin/hotels?action=add" class="btn btn-primary">+ Add Hotel</a>
        </div>

        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Location</th>
                        <th>Price/Night</th>
                        <th>Rating</th>
                        <th>Featured</th>
                        <th>Active</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="hotel" items="${hotels}">
                        <tr>
                            <td><strong>${hotel.name}</strong></td>
                            <td>${hotel.location}</td>
                            <td>$<fmt:formatNumber value="${hotel.pricePerNight}" pattern="#,##0.00"/></td>
                            <td>⭐ ${hotel.rating}</td>
                            <td><span class="badge badge-${hotel.featured ? 'success' : 'secondary'}">${hotel.featured ? 'Yes' : 'No'}</span></td>
                            <td><span class="badge badge-${hotel.active ? 'success' : 'danger'}">${hotel.active ? 'Active' : 'Inactive'}</span></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/hotels?action=edit&id=${hotel.idString}" class="btn btn-outline btn-sm">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/hotels?action=delete&id=${hotel.idString}"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Delete this hotel?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${empty hotels}">
            <div class="empty-state">
                <div class="empty-icon">🏨</div>
                <h4>No hotels yet</h4>
                <a href="${pageContext.request.contextPath}/admin/hotels?action=add" class="btn btn-primary">Add First Hotel</a>
            </div>
        </c:if>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>

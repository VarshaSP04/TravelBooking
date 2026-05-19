<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Trips | TravelBooking Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="sidebar.jsp"><jsp:param name="active" value="trips"/></jsp:include>

    <main class="admin-main">
        <div class="admin-header">
            <h1>Trips</h1>
            <a href="${pageContext.request.contextPath}/admin/trips?action=add" class="btn btn-primary">+ Add Trip</a>
        </div>

        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Destination</th>
                        <th>Duration</th>
                        <th>Price</th>
                        <th>Category</th>
                        <th>Bookings</th>
                        <th>Active</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="trip" items="${trips}">
                        <tr>
                            <td><strong>${trip.name}</strong></td>
                            <td>${trip.destination}</td>
                            <td>${trip.duration} days</td>
                            <td>$<fmt:formatNumber value="${trip.price}" pattern="#,##0.00"/></td>
                            <td><span class="badge badge-primary">${trip.category}</span></td>
                            <td>${trip.currentBookings}/${trip.maxGroupSize}</td>
                            <td><span class="badge badge-${trip.active ? 'success' : 'danger'}">${trip.active ? 'Active' : 'Inactive'}</span></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/trips?action=edit&id=${trip.idString}" class="btn btn-outline btn-sm">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/trips?action=delete&id=${trip.idString}"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Delete this trip?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${empty trips}">
            <div class="empty-state">
                <div class="empty-icon">🌴</div>
                <h4>No trips yet</h4>
                <a href="${pageContext.request.contextPath}/admin/trips?action=add" class="btn btn-primary">Add First Trip</a>
            </div>
        </c:if>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | TravelBooking</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="sidebar.jsp"><jsp:param name="active" value="dashboard"/></jsp:include>

    <main class="admin-main">
        <div class="admin-header">
            <h1>Dashboard</h1>
            <span>Welcome, ${sessionScope.adminName}</span>
        </div>

        <div class="admin-stats">
            <div class="stat-card">
                <div class="stat-icon">👥</div>
                <div class="stat-info">
                    <span class="stat-value">${totalUsers}</span>
                    <span class="stat-label">Users</span>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">🏨</div>
                <div class="stat-info">
                    <span class="stat-value">${totalHotels}</span>
                    <span class="stat-label">Hotels</span>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">🌴</div>
                <div class="stat-info">
                    <span class="stat-value">${totalTrips}</span>
                    <span class="stat-label">Trips</span>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">📋</div>
                <div class="stat-info">
                    <span class="stat-value">${totalBookings}</span>
                    <span class="stat-label">Bookings</span>
                </div>
            </div>
        </div>

        <div class="admin-stats" style="grid-template-columns: repeat(3,1fr);">
            <div class="stat-card">
                <div class="stat-icon">✅</div>
                <div class="stat-info">
                    <span class="stat-value">${confirmedBookings}</span>
                    <span class="stat-label">Confirmed</span>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">⏳</div>
                <div class="stat-info">
                    <span class="stat-value">${pendingBookings}</span>
                    <span class="stat-label">Pending</span>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">💰</div>
                <div class="stat-info">
                    <span class="stat-value">$<fmt:formatNumber value="${totalRevenue}" pattern="#,##0.00"/></span>
                    <span class="stat-label">Revenue</span>
                </div>
            </div>
        </div>

        <div class="section">
            <h3 style="margin-bottom:var(--space-lg);">Recent Bookings</h3>
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Reference</th>
                            <th>Type</th>
                            <th>Status</th>
                            <th>Payment</th>
                            <th>Amount</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="booking" items="${recentBookings}">
                            <tr>
                                <td><strong>${booking.referenceName}</strong></td>
                                <td><span class="badge badge-${booking.bookingType == 'HOTEL' ? 'primary' : 'accent'}">${booking.bookingType}</span></td>
                                <td><span class="badge badge-${booking.status == 'CONFIRMED' ? 'success' : booking.status == 'CANCELLED' ? 'danger' : 'warning'}">${booking.status}</span></td>
                                <td><span class="badge badge-${booking.paymentStatus == 'PAID' ? 'success' : 'warning'}">${booking.paymentStatus}</span></td>
                                <td>$<fmt:formatNumber value="${booking.totalPrice}" pattern="#,##0.00"/></td>
                                <td><fmt:formatDate value="${booking.createdAt}" pattern="MMM dd, yyyy"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>

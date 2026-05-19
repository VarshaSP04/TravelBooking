<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Dashboard"/><jsp:param name="css" value="dashboard"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div class="page-wrapper">
    <div class="container">
        <div class="dash-welcome fade-in">
            <h2>Welcome back, ${sessionScope.userName}!</h2>
            <p>Here's an overview of your travel bookings.</p>
        </div>

        <div class="dash-grid fade-in">
            <div class="stat-card">
                <div class="stat-icon">📋</div>
                <div class="stat-info">
                    <span class="stat-value">${totalBookings}</span>
                    <span class="stat-label">Total Bookings</span>
                </div>
            </div>
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
                <div class="stat-icon">🌍</div>
                <div class="stat-info">
                    <a href="${pageContext.request.contextPath}/trips" class="btn btn-primary btn-sm">Explore Trips</a>
                </div>
            </div>
        </div>

        <div class="section fade-in">
            <div class="section-header">
                <h3>Recent Bookings</h3>
                <a href="${pageContext.request.contextPath}/booking/history" class="btn btn-outline btn-sm">View All</a>
            </div>

            <c:choose>
                <c:when test="${empty recentBookings}">
                    <div class="empty-state">
                        <div class="empty-icon">🧳</div>
                        <h4>No bookings yet</h4>
                        <p>Start exploring hotels and trips to make your first booking!</p>
                        <a href="${pageContext.request.contextPath}/hotels" class="btn btn-primary">Browse Hotels</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Booking</th>
                                    <th>Type</th>
                                    <th>Route</th>
                                    <th>Check In</th>
                                    <th>Status</th>
                                    <th>Amount</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="booking" items="${recentBookings}">
                                    <tr>
                                        <td><strong>${booking.referenceName}</strong></td>
                                        <td><span class="badge badge-${booking.bookingType == 'HOTEL' ? 'primary' : 'accent'}">${booking.bookingType}</span></td>
                                        <td>
                                            <c:if test="${not empty booking.origin}">${booking.origin} &rarr; ${booking.destination}</c:if>
                                            <c:if test="${empty booking.origin}">-</c:if>
                                        </td>
                                        <td><fmt:formatDate value="${booking.checkIn}" pattern="MMM dd, yyyy"/></td>
                                        <td>
                                            <span class="badge badge-${booking.status == 'CONFIRMED' ? 'success' : booking.status == 'CANCELLED' ? 'danger' : 'warning'}">${booking.status}</span>
                                        </td>
                                        <td><strong>$<fmt:formatNumber value="${booking.totalPrice}" pattern="#,##0.00"/></strong></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

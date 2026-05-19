<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Bookings | TravelBooking Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="sidebar.jsp"><jsp:param name="active" value="bookings"/></jsp:include>

    <main class="admin-main">
        <div class="admin-header">
            <h1>Bookings</h1>
            <span class="badge badge-primary">${bookings.size()} total</span>
        </div>

        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Reference</th>
                        <th>Customer</th>
                        <th>Type</th>
                        <th>Check In</th>
                        <th>Guests</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Payment</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="booking" items="${bookings}">
                        <tr>
                            <td><strong>${booking.referenceName}</strong></td>
                            <td>
                                <div>${booking.userName != null ? booking.userName : 'N/A'}</div>
                                <small style="color:var(--text-secondary);">${booking.userEmail}</small>
                            </td>
                            <td><span class="badge badge-${booking.bookingType == 'HOTEL' ? 'primary' : 'accent'}">${booking.bookingType}</span></td>
                            <td><fmt:formatDate value="${booking.checkIn}" pattern="MMM dd, yyyy"/></td>
                            <td>${booking.guests}</td>
                            <td><strong>$<fmt:formatNumber value="${booking.totalPrice}" pattern="#,##0.00"/></strong></td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/admin/bookings" style="display:inline;">
                                    <input type="hidden" name="id" value="${booking.idString}"/>
                                    <input type="hidden" name="action" value="updateStatus"/>
                                    <select name="status" class="form-control" style="font-size:var(--fs-xs);padding:4px 8px;width:auto;" onchange="this.form.submit();">
                                        <option value="PENDING" ${booking.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                                        <option value="CONFIRMED" ${booking.status == 'CONFIRMED' ? 'selected' : ''}>Confirmed</option>
                                        <option value="CANCELLED" ${booking.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                                        <option value="COMPLETED" ${booking.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                                    </select>
                                </form>
                            </td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/admin/bookings" style="display:inline;">
                                    <input type="hidden" name="id" value="${booking.idString}"/>
                                    <input type="hidden" name="action" value="updatePayment"/>
                                    <select name="paymentStatus" class="form-control" style="font-size:var(--fs-xs);padding:4px 8px;width:auto;" onchange="this.form.submit();">
                                        <option value="PENDING" ${booking.paymentStatus == 'PENDING' ? 'selected' : ''}>Pending</option>
                                        <option value="PAID" ${booking.paymentStatus == 'PAID' ? 'selected' : ''}>Paid</option>
                                        <option value="REFUNDED" ${booking.paymentStatus == 'REFUNDED' ? 'selected' : ''}>Refunded</option>
                                    </select>
                                </form>
                            </td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/admin/bookings" style="display:inline;">
                                    <input type="hidden" name="id" value="${booking.idString}"/>
                                    <input type="hidden" name="action" value="delete"/>
                                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Delete this booking?');">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${empty bookings}">
            <div class="empty-state">
                <div class="empty-icon">📋</div>
                <h4>No bookings yet</h4>
            </div>
        </c:if>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Booking History"/><jsp:param name="css" value="booking"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div class="page-wrapper">
    <div class="container">
        <div class="section-header fade-in">
            <h2 class="section-title">My Bookings</h2>
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline btn-sm">Back to Dashboard</a>
        </div>

        <c:choose>
            <c:when test="${empty bookings}">
                <div class="empty-state fade-in">
                    <div class="empty-icon">📭</div>
                    <h4>No bookings found</h4>
                    <p>You haven't made any bookings yet. Start exploring!</p>
                    <a href="${pageContext.request.contextPath}/hotels" class="btn btn-primary">Browse Hotels</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive fade-in">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Booking</th>
                                <th>Type</th>
                                <th>Route</th>
                                <th>Check In</th>
                                <th>Check Out</th>
                                <th>Guests</th>
                                <th>Class / Room</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="booking" items="${bookings}">
                                <tr>
                                    <td><strong>${booking.referenceName}</strong></td>
                                    <td><span class="badge badge-${booking.bookingType == 'HOTEL' ? 'primary' : 'accent'}">${booking.bookingType}</span></td>
                                    <td>
                                        <c:if test="${not empty booking.origin}">${booking.origin} &rarr; ${booking.destination}</c:if>
                                        <c:if test="${empty booking.origin}">-</c:if>
                                    </td>
                                    <td><fmt:formatDate value="${booking.checkIn}" pattern="MMM dd, yyyy"/></td>
                                    <td><fmt:formatDate value="${booking.checkOut}" pattern="MMM dd, yyyy"/></td>
                                    <td>${booking.guests}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${booking.bookingType == 'FLIGHT' || booking.bookingType == 'TRAIN'}">
                                                <span class="badge badge-info">${booking.roomType}</span>
                                            </c:when>
                                            <c:when test="${not empty booking.roomType}">
                                                ${booking.roomType}
                                            </c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><strong>$<fmt:formatNumber value="${booking.totalPrice}" pattern="#,##0.00"/></strong></td>
                                    <td>
                                        <span class="badge badge-${booking.status == 'CONFIRMED' ? 'success' : booking.status == 'CANCELLED' ? 'danger' : 'warning'}">${booking.status}</span>
                                    </td>
                                    <td>
                                        <c:if test="${booking.status != 'CANCELLED'}">
                                            <form method="post" action="${pageContext.request.contextPath}/booking" style="display:inline;">
                                                <input type="hidden" name="action" value="cancel"/>
                                                <input type="hidden" name="bookingId" value="${booking.idString}"/>
                                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Cancel this booking?');">Cancel</button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

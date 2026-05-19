<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Book Now"/><jsp:param name="css" value="booking"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div class="page-wrapper">
    <div class="container">
        <h2 class="section-title fade-in">Complete Your Booking</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <div class="booking-form-card fade-in">
            <div class="booking-summary">
                <h3>Booking Summary</h3>
                <c:if test="${bookingType == 'HOTEL'}">
                    <div class="booking-summary-row"><span>Hotel</span><span><strong>${hotel.name}</strong></span></div>
                    <div class="booking-summary-row"><span>Location</span><span>${hotel.location}</span></div>
                    <div class="booking-summary-row"><span>Starting at</span><span><strong>$<fmt:formatNumber value="${hotel.pricePerNight}" pattern="#,##0.00"/></strong> / night</span></div>
                </c:if>
                <c:if test="${bookingType == 'TRIP'}">
                    <div class="booking-summary-row"><span>Trip</span><span><strong>${trip.name}</strong></span></div>
                    <div class="booking-summary-row"><span>Destination</span><span>${trip.destination}</span></div>
                    <div class="booking-summary-row"><span>Duration</span><span>${trip.duration} Days</span></div>
                    <div class="booking-summary-row"><span>Price</span><span><strong>$<fmt:formatNumber value="${trip.price}" pattern="#,##0.00"/></strong> / person</span></div>
                </c:if>
                <c:if test="${bookingType == 'FLIGHT'}">
                    <div class="booking-summary-row"><span>Flight</span><span><strong>${flight.flightNumber}</strong></span></div>
                    <div class="booking-summary-row"><span>Airline</span><span>${flight.airline}</span></div>
                    <div class="booking-summary-row"><span>Route</span><span>${flight.origin} &rarr; ${flight.destination}</span></div>
                    <div class="booking-summary-row"><span>Price</span><span><strong>$<fmt:formatNumber value="${flight.price}" pattern="#,##0.00"/></strong> / person</span></div>
                </c:if>
                <c:if test="${bookingType == 'TRAIN'}">
                    <div class="booking-summary-row"><span>Train</span><span><strong>${train.trainNumber}</strong></span></div>
                    <div class="booking-summary-row"><span>Name</span><span>${train.trainName}</span></div>
                    <div class="booking-summary-row"><span>Route</span><span>${train.origin} &rarr; ${train.destination}</span></div>
                    <div class="booking-summary-row"><span>Price</span><span><strong>$<fmt:formatNumber value="${train.price}" pattern="#,##0.00"/></strong> / person</span></div>
                </c:if>
            </div>

            <form method="post" action="${pageContext.request.contextPath}/booking">
                <input type="hidden" name="bookingType" value="${bookingType}"/>
                <input type="hidden" name="refId" value="${refId}"/>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">Check-in Date</label>
                        <input type="date" name="checkIn" class="form-control" required/>
                    </div>
                    <c:if test="${bookingType == 'HOTEL'}">
                        <div class="form-group">
                            <label class="form-label">Check-out Date</label>
                            <input type="date" name="checkOut" class="form-control" required/>
                        </div>
                    </c:if>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">Number of Guests</label>
                        <input type="number" name="guests" class="form-control" min="1" max="10" value="1" required/>
                    </div>
                    <c:if test="${bookingType == 'HOTEL'}">
                        <div class="form-group">
                            <label class="form-label">Room Type</label>
                            <select name="roomType" class="form-control">
                                <c:forEach var="rt" items="${hotel.roomTypes}">
                                    <option value="${rt.type}">${rt.type} - $<fmt:formatNumber value="${rt.price}" pattern="#,##0.00"/>/night (${rt.available} available)</option>
                                </c:forEach>
                                <c:if test="${empty hotel.roomTypes}">
                                    <option value="Standard">Standard - $<fmt:formatNumber value="${hotel.pricePerNight}" pattern="#,##0.00"/>/night</option>
                                </c:if>
                            </select>
                        </div>
                    </c:if>
                    <c:if test="${bookingType == 'FLIGHT'}">
                        <div class="form-group">
                            <label class="form-label">Class</label>
                            <select name="roomType" class="form-control">
                                <c:forEach var="cls" items="${flight.classes}">
                                    <option value="${cls}">${cls}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </c:if>
                    <c:if test="${bookingType == 'TRAIN'}">
                        <div class="form-group">
                            <label class="form-label">Class</label>
                            <select name="roomType" class="form-control">
                                <c:forEach var="cls" items="${train.classes}">
                                    <option value="${cls}">${cls}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label class="form-label">Special Requests (optional)</label>
                    <textarea name="specialRequests" class="form-control" rows="3" placeholder="Any special requirements or preferences..."></textarea>
                </div>

                <button type="submit" class="btn btn-primary btn-block btn-lg">Confirm Booking</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"><jsp:param name="js" value="booking"/></jsp:include>

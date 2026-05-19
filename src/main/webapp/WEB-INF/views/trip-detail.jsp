<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="${trip.name}"/><jsp:param name="css" value="detail"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div class="page-wrapper">
    <div class="detail-hero">
        <c:choose>
            <c:when test="${not empty trip.images}">
                <img src="${pageContext.request.contextPath}${trip.images[0]}?v=3" alt="${trip.name}" style="width:100%;height:100%;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:100%;background:linear-gradient(135deg,#f093fb,#f5576c);display:flex;align-items:center;justify-content:center;color:white;font-size:5rem;&quot;>🌴</div>'">
            </c:when>
            <c:otherwise>
                <div style="width:100%;height:100%;background:linear-gradient(135deg,#f093fb,#f5576c);display:flex;align-items:center;justify-content:center;color:white;font-size:5rem;">🌴</div>
            </c:otherwise>
        </c:choose>
        <div class="detail-hero-overlay"><div class="container">
            <span class="badge badge-primary" style="margin-bottom:8px;">${trip.category}</span>
            <h1>${trip.name}</h1>
            <div class="location">📍 ${trip.destination} &bull; ${trip.duration} Days</div>
        </div></div>
    </div>

    <div class="container">
        <div class="detail-content">
            <div class="detail-main">
                <div class="detail-section fade-in">
                    <h3>About This Trip</h3>
                    <p class="detail-description">${trip.description}</p>
                </div>

                <div class="detail-section fade-in">
                    <h3>What's Included</h3>
                    <div class="tag-list">
                        <c:forEach var="inc" items="${trip.inclusions}">
                            <span class="tag">✓ ${inc}</span>
                        </c:forEach>
                    </div>
                </div>

                <div class="detail-section fade-in">
                    <h3>Day-by-Day Itinerary</h3>
                    <div class="itinerary-list">
                        <c:forEach var="day" items="${trip.itinerary}">
                            <div class="itinerary-item">
                                <div class="itinerary-day">DAY<span>${day.day}</span></div>
                                <div class="itinerary-info">
                                    <h4>${day.title}</h4>
                                    <p>${day.description}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="detail-sidebar">
                <div class="price-card fade-in">
                    <div><span class="price-amount">$${trip.price}</span><span class="price-label"> / person</span></div>
                    <div class="price-details">
                        <div class="price-detail-row"><span class="label">Duration</span><span class="value">${trip.duration} Days</span></div>
                        <div class="price-detail-row"><span class="label">Group Size</span><span class="value">Max ${trip.maxGroupSize}</span></div>
                        <div class="price-detail-row"><span class="label">Available</span><span class="value">${trip.availableSlots} spots</span></div>
                        <div class="price-detail-row"><span class="label">Start Date</span><span class="value"><fmt:formatDate value="${trip.startDate}" pattern="MMM dd, yyyy"/></span></div>
                    </div>
                    <a href="${pageContext.request.contextPath}/booking?type=TRIP&refId=${trip.idString}" class="btn btn-primary btn-block btn-lg">Book This Trip</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

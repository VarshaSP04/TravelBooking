<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="${hotel.name}"/><jsp:param name="css" value="detail"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div class="page-wrapper">
    <div class="detail-hero">
        <c:choose>
            <c:when test="${not empty hotel.images}">
                <img src="${pageContext.request.contextPath}${hotel.images[0]}?v=3" alt="${hotel.name}" style="width:100%;height:100%;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:100%;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:5rem;&quot;>🏨</div>'">
            </c:when>
            <c:otherwise>
                <div style="width:100%;height:100%;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:5rem;">🏨</div>
            </c:otherwise>
        </c:choose>
        <div class="detail-hero-overlay">
            <div class="container">
                <h1>${hotel.name}</h1>
                <div class="location">📍 ${hotel.location} &bull; ★ ${hotel.rating}</div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="detail-content">
            <div class="detail-main">
                <div class="detail-section fade-in">
                    <h3>About This Hotel</h3>
                    <p class="detail-description">${hotel.description}</p>
                </div>

                <div class="detail-section fade-in">
                    <h3>Amenities</h3>
                    <div class="tag-list">
                        <c:forEach var="amenity" items="${hotel.amenities}">
                            <span class="tag">${amenity}</span>
                        </c:forEach>
                    </div>
                </div>

                <div class="detail-section fade-in">
                    <h3>Room Types</h3>
                    <div class="table-wrapper">
                        <table class="table">
                            <thead><tr><th>Room Type</th><th>Price/Night</th><th>Available</th><th></th></tr></thead>
                            <tbody>
                                <c:forEach var="room" items="${hotel.roomTypes}">
                                    <tr>
                                        <td><strong>${room.type}</strong></td>
                                        <td>$${room.price}</td>
                                        <td><span class="badge badge-success">${room.available} rooms</span></td>
                                        <td><a href="${pageContext.request.contextPath}/booking?type=HOTEL&refId=${hotel.idString}&room=${room.type}" class="btn btn-primary btn-sm">Book Now</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="detail-sidebar">
                <div class="price-card fade-in">
                    <div><span class="price-amount">$${hotel.pricePerNight}</span><span class="price-label"> / night</span></div>
                    <div class="price-details">
                        <div class="price-detail-row"><span class="label">Location</span><span class="value">${hotel.location}</span></div>
                        <div class="price-detail-row"><span class="label">Rating</span><span class="value">★ ${hotel.rating}</span></div>
                        <div class="price-detail-row"><span class="label">Rooms Available</span><span class="value">${hotel.roomTypes.size()} types</span></div>
                    </div>
                    <a href="${pageContext.request.contextPath}/booking?type=HOTEL&refId=${hotel.idString}" class="btn btn-primary btn-block btn-lg">Book This Hotel</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

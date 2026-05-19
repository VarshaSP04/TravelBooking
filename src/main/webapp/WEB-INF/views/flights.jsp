<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Flights"/><jsp:param name="css" value="listing"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div style="padding-top: 80px;">
    <div class="page-header"><div class="container">
        <div class="breadcrumb"><a href="${pageContext.request.contextPath}/home">Home</a> / <span>Flights</span></div>
        <h1>Book Flights</h1>
        <p>Find the best deals on domestic and international flights</p>
    </div></div>

    <div class="container page-inner">
        <p class="listing-count">${flights.size()} flights found <c:if test="${searchOrigin != null}">from "${searchOrigin}"</c:if><c:if test="${searchDestination != null}">to "${searchDestination}"</c:if></p>

        <div class="listing-grid">
            <c:forEach var="flight" items="${flights}">
                <a href="${pageContext.request.contextPath}/flights?id=${flight.idString}" class="card hover-lift">
                    <div class="card-img-wrapper img-zoom">
                        <div class="card-badge">${flight.airline}</div>
                        <div class="card-price-badge">$${flight.price}</div>
                        <c:choose>
                            <c:when test="${not empty flight.image}">
                                <img src="${pageContext.request.contextPath}${flight.image}?v=3" alt="${flight.airline}" style="width:100%;height:220px;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:220px;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;&quot;>✈️</div>'">
                            </c:when>
                            <c:otherwise>
                                <div style="width:100%;height:220px;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;">✈️</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <h3 class="card-title">${flight.flightNumber}</h3>
                        <p class="card-text">${flight.origin} → ${flight.destination}</p>
                        <div class="card-meta">
                            <div class="card-meta-item">${flight.aircraft}</div>
                            <div class="card-meta-item">${flight.classes.size()} classes</div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>

        <c:if test="${flights.isEmpty()}">
            <div class="empty-state"><div class="empty-state-icon">✈️</div><h3>No Flights Found</h3><p>Try adjusting your search criteria</p></div>
        </c:if>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

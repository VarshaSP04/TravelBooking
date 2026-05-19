<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Hotels"/><jsp:param name="css" value="listing"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div style="padding-top: 80px;">
    <div class="page-header"><div class="container">
        <div class="breadcrumb"><a href="${pageContext.request.contextPath}/home">Home</a> / <span>Hotels</span></div>
        <h1>Explore Hotels</h1>
        <p>Find the perfect stay for your next adventure</p>
    </div></div>

    <div class="container page-inner">
        <p class="listing-count">${hotels.size()} hotels found <c:if test="${searchLocation != null}">in "${searchLocation}"</c:if></p>

        <div class="listing-grid">
            <c:forEach var="hotel" items="${hotels}">
                <a href="${pageContext.request.contextPath}/hotels?id=${hotel.idString}" class="card hover-lift">
                    <div class="card-img-wrapper img-zoom">
                        <div class="card-badge">${hotel.location}</div>
                        <div class="card-price-badge">$${hotel.pricePerNight}/night</div>
                        <c:choose>
                            <c:when test="${not empty hotel.images}">
                                <img src="${pageContext.request.contextPath}${hotel.images[0]}?v=3" alt="${hotel.name}" style="width:100%;height:220px;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:220px;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;&quot;>🏨</div>'">
                            </c:when>
                            <c:otherwise>
                                <div style="width:100%;height:220px;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;">🏨</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <h3 class="card-title">${hotel.name}</h3>
                        <p class="card-text">${hotel.description.length() > 120 ? hotel.description.substring(0,120).concat('...') : hotel.description}</p>
                        <div class="card-meta">
                            <div class="card-meta-item"><span class="rating">★</span><span class="rating-value">${hotel.rating}</span></div>
                            <div class="card-meta-item">${hotel.roomTypes.size()} room types</div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>

        <c:if test="${hotels.isEmpty()}">
            <div class="empty-state"><div class="empty-state-icon">🏨</div><h3>No Hotels Found</h3><p>Try adjusting your search criteria</p></div>
        </c:if>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

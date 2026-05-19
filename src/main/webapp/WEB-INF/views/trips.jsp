<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Trips"/><jsp:param name="css" value="listing"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div style="padding-top: 80px;">
    <div class="page-header"><div class="container">
        <div class="breadcrumb"><a href="${pageContext.request.contextPath}/home">Home</a> / <span>Trips</span></div>
        <h1>Trip Packages</h1>
        <p>Curated travel experiences for every type of explorer</p>
    </div></div>

    <div class="container page-inner">
        <div class="listing-filters">
            <a href="${pageContext.request.contextPath}/trips" class="filter-btn ${selectedCategory == null ? 'active' : ''}">All</a>
            <a href="${pageContext.request.contextPath}/trips?category=Adventure" class="filter-btn ${selectedCategory == 'Adventure' ? 'active' : ''}">Adventure</a>
            <a href="${pageContext.request.contextPath}/trips?category=Luxury" class="filter-btn ${selectedCategory == 'Luxury' ? 'active' : ''}">Luxury</a>
            <a href="${pageContext.request.contextPath}/trips?category=Cultural" class="filter-btn ${selectedCategory == 'Cultural' ? 'active' : ''}">Cultural</a>
            <a href="${pageContext.request.contextPath}/trips?category=Beach" class="filter-btn ${selectedCategory == 'Beach' ? 'active' : ''}">Beach</a>
        </div>

        <p class="listing-count">${trips.size()} trips found</p>

        <div class="listing-grid">
            <c:forEach var="trip" items="${trips}">
                <a href="${pageContext.request.contextPath}/trips?id=${trip.idString}" class="card hover-lift">
                    <div class="card-img-wrapper img-zoom">
                        <div class="card-badge">${trip.category}</div>
                        <div class="card-price-badge">$${trip.price}</div>
                        <c:choose>
                            <c:when test="${not empty trip.images}">
                                <img src="${pageContext.request.contextPath}${trip.images[0]}?v=3" alt="${trip.name}" style="width:100%;height:220px;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:220px;background:linear-gradient(135deg,#f093fb,#f5576c);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;&quot;>🌴</div>'">
                            </c:when>
                            <c:otherwise>
                                <div style="width:100%;height:220px;background:linear-gradient(135deg,#f093fb,#f5576c);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;">🌴</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <h3 class="card-title">${trip.name}</h3>
                        <p class="card-text">${trip.description.length() > 120 ? trip.description.substring(0,120).concat('...') : trip.description}</p>
                        <div class="card-meta">
                            <div class="card-meta-item">📍 ${trip.destination}</div>
                            <div class="card-meta-item">📅 ${trip.duration} Days</div>
                            <div class="card-meta-item">👥 ${trip.availableSlots} left</div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>

        <c:if test="${trips.isEmpty()}">
            <div class="empty-state"><div class="empty-state-icon">🌴</div><h3>No Trips Found</h3><p>Check back soon for new adventures</p></div>
        </c:if>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

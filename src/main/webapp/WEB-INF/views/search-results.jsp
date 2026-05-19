<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Search"/><jsp:param name="css" value="listing"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div class="page-wrapper">
    <div class="container">
        <div class="section fade-in" style="margin-bottom:var(--space-2xl);">
            <h2 class="section-title">Search</h2>
            <form method="get" action="${pageContext.request.contextPath}/search" class="search-form" style="display:flex;gap:var(--space-md);flex-wrap:wrap;align-items:flex-end;">
                <div class="form-group" style="flex:1;min-width:250px;">
                    <input type="text" name="q" class="form-control" placeholder="Search hotels, trips, destinations..." value="${query}"/>
                </div>
                <div class="form-group">
                    <select name="type" class="form-control">
                        <option value="all" ${type == 'all' ? 'selected' : ''}>All</option>
                        <option value="hotel" ${type == 'hotel' ? 'selected' : ''}>Hotels</option>
                        <option value="trip" ${type == 'trip' ? 'selected' : ''}>Trips</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>

        <c:if test="${query != null}">
            <p class="fade-in" style="color:var(--text-secondary);margin-bottom:var(--space-xl);">
                Found <strong>${totalResults}</strong> result(s) for "<strong>${query}</strong>"
            </p>
        </c:if>

        <c:if test="${not empty hotels}">
            <div class="section fade-in">
                <h3>Hotels</h3>
                <div class="listing-grid">
                    <c:forEach var="hotel" items="${hotels}">
                        <a href="${pageContext.request.contextPath}/hotel?id=${hotel.idString}" class="card card-hover">
                            <div class="card-img">
                                <c:choose>
                                    <c:when test="${not empty hotel.images}">
                                        <img src="${pageContext.request.contextPath}${hotel.images[0]}" alt="${hotel.name}" style="width:100%;height:100%;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:100%;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;&quot;>🏨</div>'">
                                    </c:when>
                                    <c:otherwise>
                                        <div style="width:100%;height:100%;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;">🏨</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="card-body">
                                <h4 class="card-title">${hotel.name}</h4>
                                <p class="card-text">📍 ${hotel.location}</p>
                                <div class="card-footer">
                                    <span class="card-price">$${hotel.pricePerNight}<small>/night</small></span>
                                    <span class="card-rating">⭐ ${hotel.rating}</span>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <c:if test="${not empty trips}">
            <div class="section fade-in">
                <h3>Trips</h3>
                <div class="listing-grid">
                    <c:forEach var="trip" items="${trips}">
                        <a href="${pageContext.request.contextPath}/trip?id=${trip.idString}" class="card card-hover">
                            <div class="card-img">
                                <c:choose>
                                    <c:when test="${not empty trip.images}">
                                        <img src="${pageContext.request.contextPath}${trip.images[0]}" alt="${trip.name}" style="width:100%;height:100%;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:100%;background:linear-gradient(135deg,#f093fb,#f5576c);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;&quot;>🌴</div>'">
                                    </c:when>
                                    <c:otherwise>
                                        <div style="width:100%;height:100%;background:linear-gradient(135deg,#f093fb,#f5576c);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;">🌴</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="card-body">
                                <span class="badge badge-primary" style="margin-bottom:4px;">${trip.category}</span>
                                <h4 class="card-title">${trip.name}</h4>
                                <p class="card-text">📍 ${trip.destination} &bull; ${trip.duration} Days</p>
                                <div class="card-footer">
                                    <span class="card-price">$${trip.price}<small>/person</small></span>
                                    <span class="card-rating">${trip.availableSlots} spots left</span>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <c:if test="${query != null && totalResults == 0}">
            <div class="empty-state fade-in">
                <div class="empty-icon">🔍</div>
                <h4>No results found</h4>
                <p>Try a different search term or browse our collections.</p>
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="includes/footer.jsp"><jsp:param name="js" value="search"/></jsp:include>

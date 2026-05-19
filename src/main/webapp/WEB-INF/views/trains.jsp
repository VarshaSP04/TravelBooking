<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Trains"/><jsp:param name="css" value="listing"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div style="padding-top: 80px;">
    <div class="page-header"><div class="container">
        <div class="breadcrumb"><a href="${pageContext.request.contextPath}/home">Home</a> / <span>Trains</span></div>
        <h1>Book Trains</h1>
        <p>Explore destinations by train - comfortable, scenic, and affordable</p>
    </div></div>

    <div class="container page-inner">
        <p class="listing-count">${trains.size()} trains found <c:if test="${searchOrigin != null}">from "${searchOrigin}"</c:if><c:if test="${searchDestination != null}">to "${searchDestination}"</c:if></p>

        <div class="listing-grid">
            <c:forEach var="train" items="${trains}">
                <a href="${pageContext.request.contextPath}/trains?id=${train.idString}" class="card hover-lift">
                    <div class="card-img-wrapper img-zoom">
                        <div class="card-badge">${train.trainName}</div>
                        <div class="card-price-badge">$${train.price}</div>
                        <c:choose>
                            <c:when test="${not empty train.image}">
                                <img src="${pageContext.request.contextPath}${train.image}?v=3" alt="${train.trainName}" style="width:100%;height:220px;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:220px;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;&quot;>🚂</div>'">
                            </c:when>
                            <c:otherwise>
                                <div style="width:100%;height:220px;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;">🚂</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <h3 class="card-title">${train.trainNumber}</h3>
                        <p class="card-text">${train.origin} → ${train.destination}</p>
                        <div class="card-meta">
                            <div class="card-meta-item">${train.trainName}</div>
                            <div class="card-meta-item">${train.classes.size()} classes</div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>

        <c:if test="${trains.isEmpty()}">
            <div class="empty-state"><div class="empty-state-icon">🚂</div><h3>No Trains Found</h3><p>Try adjusting your search criteria</p></div>
        </c:if>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

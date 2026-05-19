<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="${train.trainNumber}"/><jsp:param name="css" value="detail"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div class="page-wrapper">
    <div class="detail-hero">
        <c:choose>
            <c:when test="${not empty train.image}">
                <img src="${pageContext.request.contextPath}${train.image}?v=3" alt="${train.trainName}" style="width:100%;height:100%;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:100%;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:5rem;&quot;>🚂</div>'">
            </c:when>
            <c:otherwise>
                <div style="width:100%;height:100%;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:5rem;">🚂</div>
            </c:otherwise>
        </c:choose>
        <div class="detail-hero-overlay">
            <div class="container">
                <h1>${train.trainNumber}</h1>
                <div class="location">🚂 ${train.trainName} &bull; ${train.origin} → ${train.destination}</div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="detail-content">
            <div class="detail-main">
                <div class="detail-section fade-in">
                    <h3>Train Details</h3>
                    <p class="detail-description">Train ${train.trainNumber} - ${train.trainName}</p>
                </div>

                <div class="detail-section fade-in">
                    <h3>Route</h3>
                    <div class="table-wrapper">
                        <table class="table">
                            <thead><tr><th>Origin</th><th>Destination</th></tr></thead>
                            <tbody>
                                <tr>
                                    <td><strong>${train.origin}</strong></td>
                                    <td><strong>${train.destination}</strong></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="detail-section fade-in">
                    <h3>Available Classes</h3>
                    <div class="tag-list">
                        <c:forEach var="cls" items="${train.classes}">
                            <span class="tag">${cls}</span>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="detail-sidebar">
                <div class="price-card fade-in">
                    <div><span class="price-amount">$${train.price}</span><span class="price-label"> / person</span></div>
                    <div class="price-details">
                        <div class="price-detail-row"><span class="label">Train Name</span><span class="value">${train.trainName}</span></div>
                        <div class="price-detail-row"><span class="label">Train Number</span><span class="value">${train.trainNumber}</span></div>
                        <div class="price-detail-row"><span class="label">Classes</span><span class="value">${train.classes.size()}</span></div>
                    </div>
                    <a href="${pageContext.request.contextPath}/booking?type=TRAIN&refId=${train.idString}" class="btn btn-primary btn-block btn-lg">Book This Train</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

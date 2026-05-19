<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header class="site-header" id="siteHeader">
    <div class="nav-container">
        <a href="${pageContext.request.contextPath}/home" class="nav-logo">
            <div class="nav-logo-icon">✈</div>
            TravelBooking
        </a>

        <button class="nav-toggle" id="navToggle" aria-label="Toggle menu">
            <span></span><span></span><span></span>
        </button>

        <div class="nav-menu" id="navMenu">
            <nav class="nav-links">
                <a href="${pageContext.request.contextPath}/home" class="nav-link">Home</a>
                <a href="${pageContext.request.contextPath}/hotels" class="nav-link">Hotels</a>
                <a href="${pageContext.request.contextPath}/trips" class="nav-link">Trips</a>
                <a href="${pageContext.request.contextPath}/flights" class="nav-link">Flights</a>
                <a href="${pageContext.request.contextPath}/trains" class="nav-link">Trains</a>
                <a href="${pageContext.request.contextPath}/search" class="nav-link">Search</a>
                <c:if test="${sessionScope.user != null}">
                    <a href="${pageContext.request.contextPath}/dashboard" class="nav-link">Dashboard</a>
                </c:if>
            </nav>

            <div class="nav-actions">
                <c:if test="${sessionScope.user != null}">
                    <a href="${pageContext.request.contextPath}/profile" class="nav-btn-login">${sessionScope.userName}</a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-btn-signup">Logout</a>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <a href="${pageContext.request.contextPath}/login" class="nav-btn-login">Log In</a>
                    <a href="${pageContext.request.contextPath}/register" class="nav-btn-signup">Sign Up</a>
                </c:if>
            </div>
        </div>
    </div>
</header>

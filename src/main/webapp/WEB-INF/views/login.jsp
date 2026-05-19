<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Login"/><jsp:param name="css" value="auth"/></jsp:include>

<div class="auth-page">
    <div class="auth-card fade-in">
        <div class="auth-header">
            <a href="${pageContext.request.contextPath}/home" class="auth-logo"><div class="nav-logo-icon">✈</div> TravelBooking</a>
            <h2>Welcome Back</h2>
            <p>Sign in to your account to continue</p>
        </div>

        <c:if test="${error != null}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form id="loginForm" method="POST" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label class="form-label" for="email">Email Address</label>
                <input type="email" id="email" name="email" class="form-control" placeholder="you@example.com" value="${email}" required>
            </div>
            <div class="form-group">
                <label class="form-label" for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Enter your password" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block btn-lg">Sign In</button>
        </form>

        <div class="auth-footer">
            Don't have an account? <a href="${pageContext.request.contextPath}/register">Create one</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script src="${pageContext.request.contextPath}/js/auth.js"></script>
</body></html>

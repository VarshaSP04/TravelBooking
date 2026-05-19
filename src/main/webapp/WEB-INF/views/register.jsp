<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Register"/><jsp:param name="css" value="auth"/></jsp:include>

<div class="auth-page">
    <div class="auth-card fade-in" style="max-width:520px;">
        <div class="auth-header">
            <a href="${pageContext.request.contextPath}/home" class="auth-logo"><div class="nav-logo-icon">✈</div> TravelBooking</a>
            <h2>Create Account</h2>
            <p>Start your travel journey today</p>
        </div>

        <c:if test="${error != null}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form id="registerForm" method="POST" action="${pageContext.request.contextPath}/register">
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" class="form-control" value="${firstName}" placeholder="John" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName" class="form-control" value="${lastName}" placeholder="Doe" required>
                </div>
            </div>
            <div class="form-group">
                <label class="form-label" for="email">Email Address</label>
                <input type="email" id="email" name="email" class="form-control" value="${email}" placeholder="you@example.com" required>
            </div>
            <div class="form-group">
                <label class="form-label" for="phone">Phone Number</label>
                <input type="tel" id="phone" name="phone" class="form-control" value="${phone}" placeholder="+1234567890" required>
            </div>
            <div class="form-group">
                <label class="form-label" for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Min. 6 characters" required>
                <div class="password-strength"><div class="password-strength-bar"></div></div>
            </div>
            <div class="form-group">
                <label class="form-label" for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Repeat password" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block btn-lg">Create Account</button>
        </form>

        <div class="auth-footer">
            Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script src="${pageContext.request.contextPath}/js/auth.js"></script>
</body></html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login | TravelBooking</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-login-page">
    <div class="admin-login-card">
        <div style="text-align:center;margin-bottom:var(--space-lg);">
            <div style="font-size:2.5rem;">🛡️</div>
        </div>
        <h2>Admin Panel</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/admin/login">
            <div class="form-group">
                <label class="form-label">Username</label>
                <input type="text" name="username" class="form-control" placeholder="Enter username" required autofocus/>
            </div>
            <div class="form-group">
                <label class="form-label">Password</label>
                <input type="password" name="password" class="form-control" placeholder="Enter password" required/>
            </div>
            <button type="submit" class="btn btn-primary btn-block btn-lg" style="margin-top:var(--space-lg);">Sign In</button>
        </form>

        <p style="text-align:center;margin-top:var(--space-xl);color:rgba(255,255,255,0.4);font-size:var(--fs-sm);">
            <a href="${pageContext.request.contextPath}/home" style="color:rgba(255,255,255,0.6);">← Back to Website</a>
        </p>
    </div>
</div>
</body>
</html>

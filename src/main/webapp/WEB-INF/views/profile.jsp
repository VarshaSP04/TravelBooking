<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="My Profile"/><jsp:param name="css" value="auth"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div class="page-wrapper">
    <div class="container" style="max-width:700px;">
        <h2 class="section-title fade-in">My Profile</h2>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <div class="auth-card fade-in" style="max-width:100%;">
            <h3>Personal Information</h3>
            <form method="post" action="${pageContext.request.contextPath}/profile">
                <input type="hidden" name="action" value="updateProfile"/>
                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">First Name</label>
                        <input type="text" name="firstName" class="form-control" value="${user.firstName}" required/>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Last Name</label>
                        <input type="text" name="lastName" class="form-control" value="${user.lastName}" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" value="${user.email}" disabled/>
                    <small style="color:var(--text-secondary);">Email cannot be changed.</small>
                </div>
                <div class="form-group">
                    <label class="form-label">Phone</label>
                    <input type="tel" name="phone" class="form-control" value="${user.phone}"/>
                </div>
                <div class="form-group">
                    <label class="form-label">Address</label>
                    <textarea name="address" class="form-control" rows="3">${user.address}</textarea>
                </div>
                <button type="submit" class="btn btn-primary btn-block">Update Profile</button>
            </form>
        </div>

        <div class="auth-card fade-in" style="max-width:100%;margin-top:var(--space-xl);">
            <h3>Change Password</h3>
            <form method="post" action="${pageContext.request.contextPath}/profile">
                <input type="hidden" name="action" value="changePassword"/>
                <div class="form-group">
                    <label class="form-label">Current Password</label>
                    <input type="password" name="oldPassword" class="form-control" required/>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">New Password</label>
                        <input type="password" name="newPassword" class="form-control" required minlength="6"/>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Confirm Password</label>
                        <input type="password" name="confirmPassword" class="form-control" required minlength="6"/>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary btn-block">Change Password</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

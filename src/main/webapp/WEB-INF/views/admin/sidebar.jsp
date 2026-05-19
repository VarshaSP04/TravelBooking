<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<aside class="admin-sidebar" id="adminSidebar">
    <div class="admin-sidebar-logo">
        <span>✈</span> TravelBooking
    </div>

    <div class="admin-nav-section">
        <div class="admin-nav-label">Main</div>
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="admin-nav-link ${param.active == 'dashboard' ? 'active' : ''}">
            <span class="admin-nav-icon">📊</span> Dashboard
        </a>
    </div>

    <div class="admin-nav-section">
        <div class="admin-nav-label">Management</div>
        <a href="${pageContext.request.contextPath}/admin/users" class="admin-nav-link ${param.active == 'users' ? 'active' : ''}">
            <span class="admin-nav-icon">👥</span> Users
        </a>
        <a href="${pageContext.request.contextPath}/admin/hotels" class="admin-nav-link ${param.active == 'hotels' ? 'active' : ''}">
            <span class="admin-nav-icon">🏨</span> Hotels
        </a>
        <a href="${pageContext.request.contextPath}/admin/trips" class="admin-nav-link ${param.active == 'trips' ? 'active' : ''}">
            <span class="admin-nav-icon">🌴</span> Trips
        </a>
        <a href="${pageContext.request.contextPath}/admin/bookings" class="admin-nav-link ${param.active == 'bookings' ? 'active' : ''}">
            <span class="admin-nav-icon">📋</span> Bookings
        </a>
    </div>

    <div class="admin-nav-section">
        <div class="admin-nav-label">Account</div>
        <a href="${pageContext.request.contextPath}/home" class="admin-nav-link">
            <span class="admin-nav-icon">🌐</span> View Site
        </a>
        <form method="post" action="${pageContext.request.contextPath}/admin/login" style="margin:0;">
            <input type="hidden" name="action" value="logout"/>
            <button type="submit" class="admin-nav-link" style="width:100%;border:none;background:none;cursor:pointer;text-align:left;">
                <span class="admin-nav-icon">🚪</span> Logout
            </button>
        </form>
    </div>
</aside>

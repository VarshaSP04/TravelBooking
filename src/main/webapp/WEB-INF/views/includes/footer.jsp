<%@ page contentType="text/html;charset=UTF-8" %>
<footer class="site-footer">
    <div class="container">
        <div class="footer-grid">
            <div class="footer-brand">
                <div class="nav-logo" style="color:white;margin-bottom:0;">
                    <div class="nav-logo-icon">✈</div> TravelBooking
                </div>
                <p>Discover the world's most beautiful destinations. Book hotels, plan trips, and create unforgettable memories with us.</p>
            </div>
            <div>
                <h4 class="footer-heading">Explore</h4>
                <div class="footer-links">
                    <a href="${pageContext.request.contextPath}/hotels">Hotels</a>
                    <a href="${pageContext.request.contextPath}/trips">Trip Packages</a>
                    <a href="${pageContext.request.contextPath}/search">Search</a>
                </div>
            </div>
            <div>
                <h4 class="footer-heading">Account</h4>
                <div class="footer-links">
                    <a href="${pageContext.request.contextPath}/login">Log In</a>
                    <a href="${pageContext.request.contextPath}/register">Sign Up</a>
                    <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                </div>
            </div>
            <div>
                <h4 class="footer-heading">Support</h4>
                <div class="footer-links">
                    <a href="#">Help Center</a>
                    <a href="#">Contact Us</a>
                    <a href="#">Privacy Policy</a>
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            &copy; 2026 TravelBooking. All rights reserved.
        </div>
    </div>
</footer>

<script src="${pageContext.request.contextPath}/js/app.js"></script>
<c:if test="${param.js != null}">
    <script src="${pageContext.request.contextPath}/js/${param.js}.js"></script>
</c:if>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Home"/><jsp:param name="css" value="home"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<!-- Hero Section -->
<section class="hero">
    <div class="hero-decor"></div>
    <div class="container">
        <div class="hero-content fade-in">
            <div class="hero-badge">✨ #1 Travel Platform in 2026</div>
            <h1>Explore the World's Most <span>Beautiful Places</span></h1>
            <p class="hero-desc">Discover breathtaking destinations, luxury hotels, and curated trip packages. Your dream vacation is just a click away.</p>

            <div class="hero-search">
                <input type="text" id="heroSearch" data-action="${pageContext.request.contextPath}/search" placeholder="Search destinations, hotels, trips..." aria-label="Search">
                <button class="btn btn-secondary" id="heroSearchBtn">Search</button>
            </div>

            <div class="hero-stats">
                <div><div class="hero-stat-value count-up" data-target="${destinationCount}">0</div><div class="hero-stat-label">Destinations</div></div>
                <div><div class="hero-stat-value count-up" data-target="${hotelCount}">0</div><div class="hero-stat-label">Hotels</div></div>
                <div><div class="hero-stat-value count-up" data-target="${flightCount}">0</div><div class="hero-stat-label">Flights</div></div>
                <div><div class="hero-stat-value count-up" data-target="${trainCount}">0</div><div class="hero-stat-label">Trains</div></div>
                <div><div class="hero-stat-value count-up" data-target="${travelerCount}">0</div><div class="hero-stat-label">Happy Travelers</div></div>
            </div>
        </div>
    </div>
</section>

<!-- Featured Hotels -->
<section class="section">
    <div class="container">
        <div class="section-header fade-in">
            <span class="section-badge">Popular Stays</span>
            <h2>Featured Hotels</h2>
            <p>Hand-picked luxury accommodations for an unforgettable experience</p>
        </div>

        <div class="listing-grid stagger">
            <c:forEach var="hotel" items="${featuredHotels}">
                <a href="${pageContext.request.contextPath}/hotels?id=${hotel.idString}" class="card">
                    <div class="card-img-wrapper">
                        <div class="card-badge">${hotel.location}</div>
                        <div class="card-price-badge">$${hotel.pricePerNight}/night</div>
                        <c:choose>
                            <c:when test="${not empty hotel.images}">
                                <img src="${pageContext.request.contextPath}${hotel.images[0]}?v=3" alt="${hotel.name}" style="width:100%;height:220px;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:220px;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;&quot;>🏨</div>'">
                            </c:when>
                            <c:otherwise>
                                <div style="width:100%;height:220px;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;">🏨</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <h3 class="card-title">${hotel.name}</h3>
                        <p class="card-text">${hotel.description.length() > 100 ? hotel.description.substring(0,100).concat('...') : hotel.description}</p>
                        <div class="card-meta">
                            <div class="card-meta-item"><span class="rating">★★★★★</span><span class="rating-value">${hotel.rating}</span></div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>

        <div class="text-center mt-xl">
            <a href="${pageContext.request.contextPath}/hotels" class="btn btn-outline">View All Hotels →</a>
        </div>
    </div>
</section>

<!-- Featured Trips -->
<section class="section section-alt">
    <div class="container">
        <div class="section-header fade-in">
            <span class="section-badge">Curated Adventures</span>
            <h2>Featured Trip Packages</h2>
            <p>Expert-designed travel experiences for every type of explorer</p>
        </div>

        <div class="listing-grid stagger">
            <c:forEach var="trip" items="${featuredTrips}">
                <a href="${pageContext.request.contextPath}/trips?id=${trip.idString}" class="card">
                    <div class="card-img-wrapper">
                        <div class="card-badge">${trip.category}</div>
                        <div class="card-price-badge">$${trip.price}</div>
                        <c:choose>
                            <c:when test="${not empty trip.images}">
                                <img src="${pageContext.request.contextPath}${trip.images[0]}?v=3" alt="${trip.name}" style="width:100%;height:220px;object-fit:cover;display:block;" onerror="this.outerHTML='<div style=&quot;width:100%;height:220px;background:linear-gradient(135deg,#f093fb,#f5576c);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;&quot;>🌴</div>'">
                            </c:when>
                            <c:otherwise>
                                <div style="width:100%;height:220px;background:linear-gradient(135deg,#f093fb,#f5576c);display:flex;align-items:center;justify-content:center;color:white;font-size:3rem;">🌴</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <h3 class="card-title">${trip.name}</h3>
                        <p class="card-text">${trip.description.length() > 100 ? trip.description.substring(0,100).concat('...') : trip.description}</p>
                        <div class="card-meta">
                            <div class="card-meta-item">📍 ${trip.destination}</div>
                            <div class="card-meta-item">📅 ${trip.duration} Days</div>
                            <div class="card-meta-item">👥 ${trip.availableSlots} spots left</div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>

        <div class="text-center mt-xl">
            <a href="${pageContext.request.contextPath}/trips" class="btn btn-outline">View All Trips →</a>
        </div>
    </div>
</section>

<!-- Why Choose Us -->
<section class="section">
    <div class="container">
        <div class="section-header fade-in">
            <span class="section-badge">Why Us</span>
            <h2>Travel with Confidence</h2>
            <p>We make your journey seamless from booking to arrival</p>
        </div>

        <div class="features-grid stagger">
            <div class="feature-card">
                <div class="feature-icon" style="background:var(--primary-light);color:var(--primary);">🔒</div>
                <h4>Secure Booking</h4>
                <p>Your payments and personal data are protected with bank-level encryption</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon" style="background:#ecfdf5;color:var(--success);">💰</div>
                <h4>Best Price Guarantee</h4>
                <p>Find a lower price? We'll match it and give you an extra 10% off</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon" style="background:#fff7ed;color:var(--secondary);">🌟</div>
                <h4>24/7 Support</h4>
                <p>Our travel experts are available around the clock to help you</p>
            </div>
        </div>
    </div>
</section>

<!-- CTA Section -->
<section class="cta-section">
    <div class="container fade-in">
        <h2>Ready for Your Next Adventure?</h2>
        <p>Join thousands of travelers who trust us for their dream vacations</p>
        <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary btn-lg">Get Started Free →</a>
    </div>
</section>

<jsp:include page="includes/footer.jsp"><jsp:param name="js" value="search"/></jsp:include>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${hotel != null ? 'Edit' : 'Add'} Hotel | TravelBooking Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="sidebar.jsp"><jsp:param name="active" value="hotels"/></jsp:include>

    <main class="admin-main">
        <div class="admin-header">
            <h1>${hotel != null ? 'Edit' : 'Add'} Hotel</h1>
            <a href="${pageContext.request.contextPath}/admin/hotels" class="btn btn-outline">← Back to Hotels</a>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <div class="admin-form-card">
            <form method="post" action="${pageContext.request.contextPath}/admin/hotels">
                <c:if test="${hotel != null}">
                    <input type="hidden" name="id" value="${hotel.idString}"/>
                </c:if>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">Hotel Name *</label>
                        <input type="text" name="name" class="form-control" value="${hotel.name}" required/>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Location *</label>
                        <input type="text" name="location" class="form-control" value="${hotel.location}" required/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">Address</label>
                    <input type="text" name="address" class="form-control" value="${hotel.address}"/>
                </div>

                <div class="form-group">
                    <label class="form-label">Description</label>
                    <textarea name="description" class="form-control" rows="4">${hotel.description}</textarea>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">Price Per Night ($) *</label>
                        <input type="number" name="pricePerNight" class="form-control" step="0.01" min="0" value="${hotel != null ? hotel.pricePerNight : ''}" required/>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Rating (0-5)</label>
                        <input type="number" name="rating" class="form-control" step="0.1" min="0" max="5" value="${hotel != null ? hotel.rating : '0'}"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">Amenities (comma-separated)</label>
                    <input type="text" name="amenities" class="form-control" placeholder="WiFi, Pool, Spa, Gym, Restaurant"
                           value="<c:forEach var='a' items='${hotel.amenities}' varStatus='s'>${a}${!s.last ? ', ' : ''}</c:forEach>"/>
                </div>

                <div class="form-group">
                    <label class="form-label">Room Types</label>
                    <div class="dynamic-rows" id="roomTypeRows">
                        <c:choose>
                            <c:when test="${hotel != null && not empty hotel.roomTypes}">
                                <c:forEach var="rt" items="${hotel.roomTypes}">
                                    <div class="dynamic-row">
                                        <input type="text" name="rtType" class="form-control" placeholder="Type" value="${rt.type}"/>
                                        <input type="number" name="rtPrice" class="form-control" placeholder="Price" step="0.01" value="${rt.price}"/>
                                        <input type="number" name="rtAvailable" class="form-control" placeholder="Available" value="${rt.available}"/>
                                        <button type="button" class="btn-remove-row" onclick="this.parentElement.remove();">×</button>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="dynamic-row">
                                    <input type="text" name="rtType" class="form-control" placeholder="Type (e.g. Standard)"/>
                                    <input type="number" name="rtPrice" class="form-control" placeholder="Price" step="0.01"/>
                                    <input type="number" name="rtAvailable" class="form-control" placeholder="Available"/>
                                    <button type="button" class="btn-remove-row" onclick="this.parentElement.remove();">×</button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <span class="btn-add-row" onclick="addRoomTypeRow();">+ Add Room Type</span>
                </div>

                <div class="form-row">
                    <div class="form-group" style="display:flex;align-items:center;gap:var(--space-sm);">
                        <input type="checkbox" name="featured" id="featured" ${hotel != null && hotel.featured ? 'checked' : ''}/>
                        <label for="featured" class="form-label" style="margin:0;">Featured</label>
                    </div>
                    <div class="form-group" style="display:flex;align-items:center;gap:var(--space-sm);">
                        <input type="checkbox" name="active" id="active" ${hotel == null || hotel.active ? 'checked' : ''}/>
                        <label for="active" class="form-label" style="margin:0;">Active</label>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary btn-lg">${hotel != null ? 'Update' : 'Create'} Hotel</button>
            </form>
        </div>
    </main>
</div>
<script>
function addRoomTypeRow() {
    const container = document.getElementById('roomTypeRows');
    const row = document.createElement('div');
    row.className = 'dynamic-row';
    row.innerHTML = '<input type="text" name="rtType" class="form-control" placeholder="Type"/>' +
        '<input type="number" name="rtPrice" class="form-control" placeholder="Price" step="0.01"/>' +
        '<input type="number" name="rtAvailable" class="form-control" placeholder="Available"/>' +
        '<button type="button" class="btn-remove-row" onclick="this.parentElement.remove();">×</button>';
    container.appendChild(row);
}
</script>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>

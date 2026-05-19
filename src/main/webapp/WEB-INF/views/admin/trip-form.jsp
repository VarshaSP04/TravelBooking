<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${trip != null ? 'Edit' : 'Add'} Trip | TravelBooking Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <jsp:include page="sidebar.jsp"><jsp:param name="active" value="trips"/></jsp:include>

    <main class="admin-main">
        <div class="admin-header">
            <h1>${trip != null ? 'Edit' : 'Add'} Trip</h1>
            <a href="${pageContext.request.contextPath}/admin/trips" class="btn btn-outline">← Back to Trips</a>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <div class="admin-form-card">
            <form method="post" action="${pageContext.request.contextPath}/admin/trips">
                <c:if test="${trip != null}">
                    <input type="hidden" name="id" value="${trip.idString}"/>
                </c:if>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">Trip Name *</label>
                        <input type="text" name="name" class="form-control" value="${trip.name}" required/>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Destination *</label>
                        <input type="text" name="destination" class="form-control" value="${trip.destination}" required/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">Description</label>
                    <textarea name="description" class="form-control" rows="4">${trip.description}</textarea>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">Duration (days) *</label>
                        <input type="number" name="duration" class="form-control" min="1" value="${trip != null ? trip.duration : ''}" required/>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Price per Person ($) *</label>
                        <input type="number" name="price" class="form-control" step="0.01" min="0" value="${trip != null ? trip.price : ''}" required/>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Max Group Size *</label>
                        <input type="number" name="maxGroupSize" class="form-control" min="1" value="${trip != null ? trip.maxGroupSize : ''}" required/>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label">Category</label>
                        <select name="category" class="form-control">
                            <option value="Adventure" ${trip.category == 'Adventure' ? 'selected' : ''}>Adventure</option>
                            <option value="Luxury" ${trip.category == 'Luxury' ? 'selected' : ''}>Luxury</option>
                            <option value="Cultural" ${trip.category == 'Cultural' ? 'selected' : ''}>Cultural</option>
                            <option value="Beach" ${trip.category == 'Beach' ? 'selected' : ''}>Beach</option>
                            <option value="Mountain" ${trip.category == 'Mountain' ? 'selected' : ''}>Mountain</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Start Date</label>
                        <input type="date" name="startDate" class="form-control" value="<fmt:formatDate value='${trip.startDate}' pattern='yyyy-MM-dd'/>"/>
                    </div>
                    <div class="form-group">
                        <label class="form-label">End Date</label>
                        <input type="date" name="endDate" class="form-control" value="<fmt:formatDate value='${trip.endDate}' pattern='yyyy-MM-dd'/>"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">Inclusions (comma-separated)</label>
                    <input type="text" name="inclusions" class="form-control" placeholder="Accommodation, Meals, Transport, Guide"
                           value="<c:forEach var='inc' items='${trip.inclusions}' varStatus='s'>${inc}${!s.last ? ', ' : ''}</c:forEach>"/>
                </div>

                <div class="form-group">
                    <label class="form-label">Itinerary</label>
                    <div class="dynamic-rows" id="itineraryRows">
                        <c:choose>
                            <c:when test="${trip != null && not empty trip.itinerary}">
                                <c:forEach var="day" items="${trip.itinerary}">
                                    <div class="dynamic-row">
                                        <input type="number" name="itDay" class="form-control" placeholder="Day" style="max-width:80px;" value="${day.day}"/>
                                        <input type="text" name="itTitle" class="form-control" placeholder="Title" value="${day.title}"/>
                                        <input type="text" name="itDesc" class="form-control" placeholder="Description" value="${day.description}"/>
                                        <button type="button" class="btn-remove-row" onclick="this.parentElement.remove();">×</button>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="dynamic-row">
                                    <input type="number" name="itDay" class="form-control" placeholder="Day" style="max-width:80px;" value="1"/>
                                    <input type="text" name="itTitle" class="form-control" placeholder="Title"/>
                                    <input type="text" name="itDesc" class="form-control" placeholder="Description"/>
                                    <button type="button" class="btn-remove-row" onclick="this.parentElement.remove();">×</button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <span class="btn-add-row" onclick="addItineraryRow();">+ Add Day</span>
                </div>

                <div class="form-row">
                    <div class="form-group" style="display:flex;align-items:center;gap:var(--space-sm);">
                        <input type="checkbox" name="featured" id="featured" ${trip != null && trip.featured ? 'checked' : ''}/>
                        <label for="featured" class="form-label" style="margin:0;">Featured</label>
                    </div>
                    <div class="form-group" style="display:flex;align-items:center;gap:var(--space-sm);">
                        <input type="checkbox" name="active" id="active" ${trip == null || trip.active ? 'checked' : ''}/>
                        <label for="active" class="form-label" style="margin:0;">Active</label>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary btn-lg">${trip != null ? 'Update' : 'Create'} Trip</button>
            </form>
        </div>
    </main>
</div>
<script>
var dayCount = document.querySelectorAll('#itineraryRows .dynamic-row').length;
function addItineraryRow() {
    dayCount++;
    const container = document.getElementById('itineraryRows');
    const row = document.createElement('div');
    row.className = 'dynamic-row';
    row.innerHTML = '<input type="number" name="itDay" class="form-control" placeholder="Day" style="max-width:80px;" value="' + dayCount + '"/>' +
        '<input type="text" name="itTitle" class="form-control" placeholder="Title"/>' +
        '<input type="text" name="itDesc" class="form-control" placeholder="Description"/>' +
        '<button type="button" class="btn-remove-row" onclick="this.parentElement.remove();">×</button>';
    container.appendChild(row);
}
</script>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>

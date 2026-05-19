<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="includes/header.jsp"><jsp:param name="title" value="Error"/></jsp:include>
<jsp:include page="includes/nav.jsp"/>

<div class="page-wrapper">
    <div class="container" style="text-align:center;padding:var(--space-4xl) 0;">
        <div class="fade-in">
            <div style="font-size:6rem;margin-bottom:var(--space-lg);">
                <c:choose>
                    <c:when test="${pageContext.errorData.statusCode == 404}">🗺️</c:when>
                    <c:otherwise>⚠️</c:otherwise>
                </c:choose>
            </div>
            <h1 style="font-size:var(--fs-4xl);margin-bottom:var(--space-md);">
                <c:choose>
                    <c:when test="${pageContext.errorData.statusCode == 404}">Page Not Found</c:when>
                    <c:otherwise>Something Went Wrong</c:otherwise>
                </c:choose>
            </h1>
            <p style="color:var(--text-secondary);font-size:var(--fs-lg);margin-bottom:var(--space-2xl);">
                <c:choose>
                    <c:when test="${pageContext.errorData.statusCode == 404}">The page you're looking for doesn't exist or has been moved.</c:when>
                    <c:otherwise>An unexpected error occurred. Please try again later.</c:otherwise>
                </c:choose>
            </p>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-lg">Back to Home</a>
        </div>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>

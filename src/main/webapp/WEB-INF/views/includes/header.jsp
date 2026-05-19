<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Travel Booking - Discover amazing destinations, book hotels, and plan your dream trips.">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>${param.title != null ? param.title : 'Travel Booking'} | TravelBooking</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css?v=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css?v=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css?v=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animations.css?v=2">
    <c:if test="${param.css != null}">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/${param.css}.css?v=2">
    </c:if>
</head>
<body>

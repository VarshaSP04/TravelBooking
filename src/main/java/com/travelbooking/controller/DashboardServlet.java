package com.travelbooking.controller;

import com.travelbooking.model.Booking;
import com.travelbooking.model.User;
import com.travelbooking.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class DashboardServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String userId = user.getIdString();

        List<Booking> recentBookings = bookingService.findByUserId(userId);
        // Limit to 5 most recent for dashboard
        if (recentBookings.size() > 5) {
            recentBookings = recentBookings.subList(0, 5);
        }

        long totalBookings = recentBookings.size();
        long confirmed = recentBookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus())).count();
        long pending = recentBookings.stream()
                .filter(b -> "PENDING".equals(b.getStatus())).count();

        req.setAttribute("recentBookings", recentBookings);
        req.setAttribute("totalBookings", totalBookings);
        req.setAttribute("confirmedBookings", confirmed);
        req.setAttribute("pendingBookings", pending);
        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}

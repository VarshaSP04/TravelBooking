package com.travelbooking.controller.admin;

import com.travelbooking.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdminDashboardServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final HotelService hotelService = new HotelService();
    private final TripService tripService = new TripService();
    private final BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("totalUsers", userService.count());
        req.setAttribute("totalHotels", hotelService.count());
        req.setAttribute("totalTrips", tripService.count());
        req.setAttribute("totalBookings", bookingService.count());
        req.setAttribute("confirmedBookings", bookingService.countByStatus("CONFIRMED"));
        req.setAttribute("pendingBookings", bookingService.countByStatus("PENDING"));
        req.setAttribute("totalRevenue", bookingService.totalRevenue());
        req.setAttribute("recentBookings", bookingService.findAll().stream()
                .limit(10).toList());

        req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req, resp);
    }
}

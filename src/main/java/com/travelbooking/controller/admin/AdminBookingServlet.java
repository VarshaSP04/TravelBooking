package com.travelbooking.controller.admin;

import com.travelbooking.model.Booking;
import com.travelbooking.model.User;
import com.travelbooking.service.BookingService;
import com.travelbooking.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AdminBookingServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Booking> bookings = bookingService.findAll();

        // Enrich bookings with user info
        for (Booking b : bookings) {
            if (b.getUserId() != null) {
                User user = userService.findById(b.getUserId().toHexString());
                if (user != null) {
                    b.setUserName(user.getFullName());
                    b.setUserEmail(user.getEmail());
                }
            }
        }

        req.setAttribute("bookings", bookings);
        req.getRequestDispatcher("/WEB-INF/views/admin/bookings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        String action = req.getParameter("action");

        if ("updateStatus".equals(action)) {
            String status = req.getParameter("status");
            bookingService.updateStatus(id, status);
        } else if ("updatePayment".equals(action)) {
            String ps = req.getParameter("paymentStatus");
            bookingService.updatePaymentStatus(id, ps);
        } else if ("delete".equals(action)) {
            bookingService.delete(id);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/bookings");
    }
}

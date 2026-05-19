package com.travelbooking.controller;

import com.travelbooking.model.Hotel;
import com.travelbooking.service.HotelService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class HotelServlet extends HttpServlet {

    private final HotelService hotelService = new HotelService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        String uri = req.getRequestURI();

        if (id != null && !id.isEmpty()) {
            // Single hotel detail
            Hotel hotel = hotelService.findById(id);
            if (hotel == null) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("hotel", hotel);
            req.getRequestDispatcher("/WEB-INF/views/hotel-detail.jsp").forward(req, resp);
        } else {
            // Hotel listing
            String location = req.getParameter("location");
            List<Hotel> hotels;
            if (location != null && !location.isEmpty()) {
                hotels = hotelService.searchByLocation(location);
                req.setAttribute("searchLocation", location);
            } else {
                hotels = hotelService.findAll();
            }
            System.out.println("=== HotelsServlet: Found " + hotels.size() + " hotels ===");
            req.setAttribute("hotels", hotels);
            req.getRequestDispatcher("/WEB-INF/views/hotels.jsp").forward(req, resp);
        }
    }
}

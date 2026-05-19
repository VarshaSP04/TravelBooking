package com.travelbooking.controller;

import com.travelbooking.model.Hotel;
import com.travelbooking.model.Trip;
import com.travelbooking.service.HotelService;
import com.travelbooking.service.TripService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchServlet extends HttpServlet {

    private final HotelService hotelService = new HotelService();
    private final TripService tripService = new TripService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String query = req.getParameter("q");
        String type = req.getParameter("type"); // hotel, trip, or null (all)

        List<Hotel> hotels = new ArrayList<>();
        List<Trip> trips = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            query = query.trim();
            if (type == null || type.equals("hotel") || type.equals("all")) {
                hotels = hotelService.search(query);
            }
            if (type == null || type.equals("trip") || type.equals("all")) {
                trips = tripService.search(query);
            }
        }

        req.setAttribute("query", query);
        req.setAttribute("type", type);
        req.setAttribute("hotels", hotels);
        req.setAttribute("trips", trips);
        req.setAttribute("totalResults", hotels.size() + trips.size());
        req.getRequestDispatcher("/WEB-INF/views/search-results.jsp").forward(req, resp);
    }
}

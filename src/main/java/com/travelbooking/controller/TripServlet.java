package com.travelbooking.controller;

import com.travelbooking.model.Trip;
import com.travelbooking.service.TripService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class TripServlet extends HttpServlet {

    private final TripService tripService = new TripService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null && !id.isEmpty()) {
            Trip trip = tripService.findById(id);
            if (trip == null) { resp.sendError(404); return; }
            req.setAttribute("trip", trip);
            req.getRequestDispatcher("/WEB-INF/views/trip-detail.jsp").forward(req, resp);
        } else {
            String category = req.getParameter("category");
            List<Trip> trips;
            if (category != null && !category.isEmpty()) {
                trips = tripService.findByCategory(category);
                req.setAttribute("selectedCategory", category);
            } else {
                trips = tripService.findAll();
            }
            req.setAttribute("trips", trips);
            req.getRequestDispatcher("/WEB-INF/views/trips.jsp").forward(req, resp);
        }
    }
}

package com.travelbooking.controller;

import com.travelbooking.model.Flight;
import com.travelbooking.service.FlightService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class FlightServlet extends HttpServlet {

    private final FlightService flightService = new FlightService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null && !id.isEmpty()) {
            // Single flight detail
            Flight flight = flightService.findById(id);
            if (flight == null) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("flight", flight);
            req.getRequestDispatcher("/WEB-INF/views/flight-detail.jsp").forward(req, resp);
        } else {
            // Flight listing
            String origin = req.getParameter("origin");
            String destination = req.getParameter("destination");
            List<Flight> flights;
            if (origin != null && !origin.isEmpty()) {
                flights = flightService.searchByOrigin(origin);
                req.setAttribute("searchOrigin", origin);
            } else if (destination != null && !destination.isEmpty()) {
                flights = flightService.searchByDestination(destination);
                req.setAttribute("searchDestination", destination);
            } else {
                flights = flightService.findAll();
            }
            req.setAttribute("flights", flights);
            req.getRequestDispatcher("/WEB-INF/views/flights.jsp").forward(req, resp);
        }
    }
}

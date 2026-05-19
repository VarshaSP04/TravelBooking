package com.travelbooking.controller;

import com.travelbooking.model.Hotel;
import com.travelbooking.model.Trip;
import com.travelbooking.service.BookingService;
import com.travelbooking.service.FlightService;
import com.travelbooking.service.HotelService;
import com.travelbooking.service.TrainService;
import com.travelbooking.service.TripService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class HomeServlet extends HttpServlet {

    private final HotelService hotelService = new HotelService();
    private final TripService tripService = new TripService();
    private final BookingService bookingService = new BookingService();
    private final FlightService flightService = new FlightService();
    private final TrainService trainService = new TrainService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Hotel> allHotels = hotelService.findAll();
        List<Trip> allTrips = tripService.findAll();

        req.setAttribute("featuredHotels", allHotels);
        req.setAttribute("featuredTrips", allTrips);
        req.setAttribute("destinationCount", tripService.count());
        req.setAttribute("hotelCount", hotelService.count());
        req.setAttribute("flightCount", flightService.count());
        req.setAttribute("trainCount", trainService.count());
        req.setAttribute("travelerCount", bookingService.count());
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}

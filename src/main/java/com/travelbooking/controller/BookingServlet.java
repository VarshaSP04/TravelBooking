package com.travelbooking.controller;

import com.travelbooking.model.Booking;
import com.travelbooking.model.Flight;
import com.travelbooking.model.Hotel;
import com.travelbooking.model.Trip;
import com.travelbooking.model.Train;
import com.travelbooking.model.User;
import com.travelbooking.service.BookingService;
import com.travelbooking.service.FlightService;
import com.travelbooking.service.HotelService;
import com.travelbooking.service.TripService;
import com.travelbooking.service.TrainService;
import com.travelbooking.util.InputValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();
    private final HotelService hotelService = new HotelService();
    private final TripService tripService = new TripService();
    private final FlightService flightService = new FlightService();
    private final TrainService trainService = new TrainService();
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        User user = (User) req.getSession().getAttribute("user");

        if (uri.endsWith("/booking/history")) {
            // Booking history
            List<Booking> bookings = bookingService.findByUserId(user.getIdString());
            req.setAttribute("bookings", bookings);
            req.getRequestDispatcher("/WEB-INF/views/booking-history.jsp").forward(req, resp);
        } else {
            // Booking form — needs type and id params
            String type = req.getParameter("type"); // HOTEL, TRIP, FLIGHT, or TRAIN
            String refId = req.getParameter("refId");

            if (type == null || refId == null) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

            if ("HOTEL".equalsIgnoreCase(type)) {
                Hotel hotel = hotelService.findById(refId);
                req.setAttribute("hotel", hotel);
                req.setAttribute("bookingType", "HOTEL");
            } else if ("TRIP".equalsIgnoreCase(type)) {
                Trip trip = tripService.findById(refId);
                req.setAttribute("trip", trip);
                req.setAttribute("bookingType", "TRIP");
            } else if ("FLIGHT".equalsIgnoreCase(type)) {
                Flight flight = flightService.findById(refId);
                req.setAttribute("flight", flight);
                req.setAttribute("bookingType", "FLIGHT");
            } else if ("TRAIN".equalsIgnoreCase(type)) {
                Train train = trainService.findById(refId);
                req.setAttribute("train", train);
                req.setAttribute("bookingType", "TRAIN");
            }
            req.setAttribute("refId", refId);
            req.getRequestDispatcher("/WEB-INF/views/booking.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String action = req.getParameter("action");

        if ("cancel".equals(action)) {
            String bookingId = req.getParameter("bookingId");
            bookingService.updateStatus(bookingId, "CANCELLED");
            resp.sendRedirect(req.getContextPath() + "/booking/history");
            return;
        }

        // Create new booking
        String type = req.getParameter("bookingType");
        String refId = req.getParameter("refId");
        String checkInStr = req.getParameter("checkIn");
        String checkOutStr = req.getParameter("checkOut");
        String guestsStr = req.getParameter("guests");
        String roomType = req.getParameter("roomType");
        String specialRequests = InputValidator.sanitize(req.getParameter("specialRequests"));

        try {
            Booking booking = new Booking();
            booking.setUserId(user.getId());
            booking.setBookingType(type.toUpperCase());
            booking.setReferenceId(new ObjectId(refId));

            Date checkIn = DATE_FMT.parse(checkInStr);
            booking.setCheckIn(checkIn);
            int guests = Integer.parseInt(guestsStr);
            booking.setGuests(guests);
            booking.setSpecialRequests(specialRequests);

            if ("HOTEL".equalsIgnoreCase(type)) {
                Hotel hotel = hotelService.findById(refId);
                if (hotel == null) {
                    throw new IllegalArgumentException("Hotel not found.");
                }
                booking.setReferenceName(hotel.getName());
                booking.setRoomType(roomType != null ? roomType : "Standard");
                Date checkOut = DATE_FMT.parse(checkOutStr);
                booking.setCheckOut(checkOut);

                long days = (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
                if (days <= 0) days = 1;
                double pricePerNight = hotel.getPricePerNight();
                for (Hotel.RoomType rt : hotel.getRoomTypes()) {
                    if (rt.getType().equalsIgnoreCase(roomType)) {
                        pricePerNight = rt.getPrice();
                        break;
                    }
                }
                booking.setTotalPrice(pricePerNight * days);
            } else if ("TRIP".equalsIgnoreCase(type)) {
                Trip trip = tripService.findById(refId);
                if (trip == null) {
                    throw new IllegalArgumentException("Trip not found.");
                }
                booking.setReferenceName(trip.getName());
                booking.setCheckOut(trip.getEndDate());
                booking.setTotalPrice(trip.getPrice() * guests);
                tripService.incrementBookings(refId);
            } else if ("FLIGHT".equalsIgnoreCase(type)) {
                Flight flight = flightService.findById(refId);
                if (flight == null) {
                    throw new IllegalArgumentException("Flight not found.");
                }
                booking.setReferenceName(flight.getFlightNumber() + " - " + flight.getAirline());
                booking.setCheckOut(flight.getArrivalTime());
                booking.setRoomType(roomType != null ? roomType : "Economy");
                booking.setOrigin(flight.getOrigin());
                booking.setDestination(flight.getDestination());
                booking.setTotalPrice(flight.getPrice() * guests);
            } else if ("TRAIN".equalsIgnoreCase(type)) {
                Train train = trainService.findById(refId);
                if (train == null) {
                    throw new IllegalArgumentException("Train not found.");
                }
                booking.setReferenceName(train.getTrainNumber() + " - " + train.getTrainName());
                booking.setCheckOut(train.getArrivalTime());
                booking.setRoomType(roomType != null ? roomType : "Sleeper");
                booking.setOrigin(train.getOrigin());
                booking.setDestination(train.getDestination());
                booking.setTotalPrice(train.getPrice() * guests);
            }

            booking.setStatus("CONFIRMED");
            booking.setPaymentStatus("PAID");
            bookingService.create(booking);

            resp.sendRedirect(req.getContextPath() + "/booking/history");

        } catch (Exception e) {
            req.setAttribute("error", "Booking failed: " + e.getMessage());
            doGet(req, resp);
        }
    }
}

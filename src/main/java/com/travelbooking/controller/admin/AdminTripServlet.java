package com.travelbooking.controller.admin;

import com.travelbooking.model.Trip;
import com.travelbooking.service.TripService;
import com.travelbooking.util.InputValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class AdminTripServlet extends HttpServlet {

    private final TripService tripService = new TripService();
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("id");

        if ("edit".equals(action) && id != null) {
            Trip trip = tripService.findById(id);
            req.setAttribute("trip", trip);
            req.getRequestDispatcher("/WEB-INF/views/admin/trip-form.jsp").forward(req, resp);
        } else if ("add".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/admin/trip-form.jsp").forward(req, resp);
        } else if ("delete".equals(action) && id != null) {
            tripService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/trips");
        } else {
            List<Trip> trips = tripService.findAllIncludingInactive();
            req.setAttribute("trips", trips);
            req.getRequestDispatcher("/WEB-INF/views/admin/trips.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");

        Trip trip = (id != null && !id.isEmpty()) ? tripService.findById(id) : new Trip();
        if (id != null && !id.isEmpty()) {
            trip.setId(new ObjectId(id));
        }

        try {
            trip.setName(InputValidator.clean(req.getParameter("name")));
            trip.setDescription(InputValidator.clean(req.getParameter("description")));
            trip.setDestination(InputValidator.clean(req.getParameter("destination")));
            trip.setDuration(Integer.parseInt(req.getParameter("duration")));
            trip.setPrice(Double.parseDouble(req.getParameter("price")));
            trip.setMaxGroupSize(Integer.parseInt(req.getParameter("maxGroupSize")));
            trip.setCategory(InputValidator.clean(req.getParameter("category")));
            trip.setFeatured("on".equals(req.getParameter("featured")));
            trip.setActive("on".equals(req.getParameter("active")));

            String startStr = req.getParameter("startDate");
            String endStr = req.getParameter("endDate");
            if (startStr != null && !startStr.isEmpty()) trip.setStartDate(DATE_FMT.parse(startStr));
            if (endStr != null && !endStr.isEmpty()) trip.setEndDate(DATE_FMT.parse(endStr));

            String inclusionsStr = req.getParameter("inclusions");
            if (inclusionsStr != null) {
                trip.setInclusions(Arrays.asList(inclusionsStr.split("\\s*,\\s*")));
            }

            // Parse itinerary
            String[] days = req.getParameterValues("itDay");
            String[] titles = req.getParameterValues("itTitle");
            String[] descs = req.getParameterValues("itDesc");
            if (days != null) {
                trip.getItinerary().clear();
                for (int i = 0; i < days.length; i++) {
                    if (!titles[i].isEmpty()) {
                        trip.getItinerary().add(new Trip.ItineraryDay(
                                Integer.parseInt(days[i]), titles[i], descs[i]));
                    }
                }
            }

            if (id != null && !id.isEmpty()) {
                tripService.update(trip);
            } else {
                tripService.create(trip);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Invalid input: " + e.getMessage());
            req.setAttribute("trip", trip);
            req.getRequestDispatcher("/WEB-INF/views/admin/trip-form.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/trips");
    }
}

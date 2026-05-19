package com.travelbooking.controller;

import com.travelbooking.listener.AppStartupListener;
import com.travelbooking.model.Hotel;
import com.travelbooking.model.Trip;
import com.travelbooking.service.HotelService;
import com.travelbooking.service.TripService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/seed-data")
public class SeedDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/plain");
        java.io.PrintWriter out = response.getWriter();
        
        try {
            out.println("=== Manual Seed Data Triggered ===");
            
            HotelService hotelService = new HotelService();
            TripService tripService = new TripService();
            
            // Activate all existing hotels
            int activatedHotels = 0;
            for (Hotel h : hotelService.findAllIncludingInactive()) {
                if (!h.isActive()) {
                    h.setActive(true);
                    hotelService.update(h);
                    activatedHotels++;
                }
            }
            out.println("Activated " + activatedHotels + " hotels");
            
            // Activate all existing trips
            int activatedTrips = 0;
            for (Trip t : tripService.findAllIncludingInactive()) {
                if (!t.isActive()) {
                    t.setActive(true);
                    tripService.update(t);
                    activatedTrips++;
                }
            }
            out.println("Activated " + activatedTrips + " trips");
            
            // Check current state
            long hotelCount = hotelService.count();
            long tripCount = tripService.count();
            
            out.println("Current active hotel count: " + hotelCount);
            out.println("Current active trip count: " + tripCount);
            
            // Manually call the seeding logic
            AppStartupListener listener = new AppStartupListener();
            
            // Call the seed method directly
            java.lang.reflect.Method seedMethod = AppStartupListener.class.getDeclaredMethod("seedSampleData");
            seedMethod.setAccessible(true);
            seedMethod.invoke(listener);
            
            // Check after seeding
            long newHotelCount = hotelService.count();
            long newTripCount = tripService.count();
            
            out.println("After seed - hotel count: " + newHotelCount);
            out.println("After seed - trip count: " + newTripCount);
            out.println("Hotels added: " + (newHotelCount - hotelCount));
            out.println("Trips added: " + (newTripCount - tripCount));
            
            out.println("=== Seed Data Completed Successfully ===");
            response.setStatus(200);
            
        } catch (Exception e) {
            out.println("ERROR: " + e.getMessage());
            e.printStackTrace(out);
            response.setStatus(500);
        }
    }
}

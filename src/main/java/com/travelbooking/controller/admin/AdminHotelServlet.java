package com.travelbooking.controller.admin;

import com.travelbooking.model.Hotel;
import com.travelbooking.service.HotelService;
import com.travelbooking.util.InputValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AdminHotelServlet extends HttpServlet {

    private final HotelService hotelService = new HotelService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("id");

        if ("edit".equals(action) && id != null) {
            Hotel hotel = hotelService.findById(id);
            req.setAttribute("hotel", hotel);
            req.getRequestDispatcher("/WEB-INF/views/admin/hotel-form.jsp").forward(req, resp);
        } else if ("add".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/admin/hotel-form.jsp").forward(req, resp);
        } else if ("delete".equals(action) && id != null) {
            hotelService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/hotels");
        } else {
            List<Hotel> hotels = hotelService.findAllIncludingInactive();
            req.setAttribute("hotels", hotels);
            req.getRequestDispatcher("/WEB-INF/views/admin/hotels.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");

        Hotel hotel = (id != null && !id.isEmpty()) ? hotelService.findById(id) : new Hotel();
        if (id != null && !id.isEmpty()) {
            hotel.setId(new ObjectId(id));
        }

        hotel.setName(InputValidator.clean(req.getParameter("name")));
        hotel.setDescription(InputValidator.clean(req.getParameter("description")));
        hotel.setLocation(InputValidator.clean(req.getParameter("location")));
        hotel.setAddress(InputValidator.clean(req.getParameter("address")));
        hotel.setPricePerNight(Double.parseDouble(req.getParameter("pricePerNight")));
        hotel.setRating(Double.parseDouble(req.getParameter("rating")));
        hotel.setFeatured("on".equals(req.getParameter("featured")));
        hotel.setActive("on".equals(req.getParameter("active")));

        String amenitiesStr = req.getParameter("amenities");
        if (amenitiesStr != null) {
            hotel.setAmenities(Arrays.asList(amenitiesStr.split("\\s*,\\s*")));
        }

        // Parse room types
        String[] rtTypes = req.getParameterValues("rtType");
        String[] rtPrices = req.getParameterValues("rtPrice");
        String[] rtAvail = req.getParameterValues("rtAvailable");
        if (rtTypes != null) {
            hotel.getRoomTypes().clear();
            for (int i = 0; i < rtTypes.length; i++) {
                if (!rtTypes[i].isEmpty()) {
                    hotel.getRoomTypes().add(new Hotel.RoomType(
                            rtTypes[i],
                            Double.parseDouble(rtPrices[i]),
                            Integer.parseInt(rtAvail[i])));
                }
            }
        }

        if (id != null && !id.isEmpty()) {
            hotelService.update(hotel);
        } else {
            hotelService.create(hotel);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/hotels");
    }
}

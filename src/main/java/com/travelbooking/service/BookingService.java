package com.travelbooking.service;

import com.travelbooking.dao.BookingDAO;
import com.travelbooking.model.Booking;
import org.bson.types.ObjectId;
import java.util.List;

public class BookingService {

    private final BookingDAO bookingDAO = new BookingDAO();

    public ObjectId create(Booking booking) { return bookingDAO.insert(booking); }
    public Booking findById(String id) { return bookingDAO.findById(id); }
    public List<Booking> findByUserId(String userId) { return bookingDAO.findByUserId(userId); }
    public List<Booking> findAll() { return bookingDAO.findAll(); }
    public boolean updateStatus(String id, String status) { return bookingDAO.updateStatus(id, status); }
    public boolean updatePaymentStatus(String id, String ps) { return bookingDAO.updatePaymentStatus(id, ps); }
    public boolean delete(String id) { return bookingDAO.delete(id); }
    public long count() { return bookingDAO.count(); }
    public long countByStatus(String status) { return bookingDAO.countByStatus(status); }
    public double totalRevenue() { return bookingDAO.totalRevenue(); }
}

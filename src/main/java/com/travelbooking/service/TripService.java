package com.travelbooking.service;

import com.travelbooking.dao.TripDAO;
import com.travelbooking.model.Trip;
import org.bson.types.ObjectId;
import java.util.List;

public class TripService {

    private final TripDAO tripDAO = new TripDAO();

    public ObjectId create(Trip trip) { return tripDAO.insert(trip); }
    public Trip findById(String id) { return tripDAO.findById(id); }
    public List<Trip> findAll() { return tripDAO.findAll(); }
    public List<Trip> findAllIncludingInactive() { return tripDAO.findAllIncludingInactive(); }
    public List<Trip> findFeatured() { return tripDAO.findFeatured(); }
    public List<Trip> findByCategory(String category) { return tripDAO.findByCategory(category); }
    public List<Trip> search(String query) { return tripDAO.search(query); }
    public boolean update(Trip trip) { return tripDAO.update(trip); }
    public boolean incrementBookings(String id) { return tripDAO.incrementBookings(id); }
    public boolean delete(String id) { return tripDAO.delete(id); }
    public long count() { return tripDAO.count(); }
}

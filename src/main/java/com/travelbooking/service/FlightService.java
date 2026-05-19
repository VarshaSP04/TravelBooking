package com.travelbooking.service;

import com.travelbooking.dao.FlightDAO;
import com.travelbooking.model.Flight;
import org.bson.types.ObjectId;
import java.util.List;

public class FlightService {

    private final FlightDAO flightDAO = new FlightDAO();

    public ObjectId create(Flight flight) { return flightDAO.insert(flight); }
    public Flight findById(String id) { return flightDAO.findById(id); }
    public List<Flight> findAll() { return flightDAO.findAll(); }
    public List<Flight> findAllIncludingInactive() { return flightDAO.findAllIncludingInactive(); }
    public List<Flight> findFeatured() { return flightDAO.findFeatured(); }
    public List<Flight> searchByOrigin(String origin) { return flightDAO.searchByOrigin(origin); }
    public List<Flight> searchByDestination(String destination) { return flightDAO.searchByDestination(destination); }
    public List<Flight> search(String query) { return flightDAO.search(query); }
    public boolean update(Flight flight) { return flightDAO.update(flight); }
    public boolean delete(String id) { return flightDAO.delete(id); }
    public long count() { return flightDAO.count(); }
}

package com.travelbooking.service;

import com.travelbooking.dao.HotelDAO;
import com.travelbooking.model.Hotel;
import org.bson.types.ObjectId;
import java.util.List;

public class HotelService {

    private final HotelDAO hotelDAO = new HotelDAO();

    public ObjectId create(Hotel hotel) { return hotelDAO.insert(hotel); }
    public Hotel findById(String id) { return hotelDAO.findById(id); }
    public List<Hotel> findAll() { return hotelDAO.findAll(); }
    public List<Hotel> findAllIncludingInactive() { return hotelDAO.findAllIncludingInactive(); }
    public List<Hotel> findFeatured() { return hotelDAO.findFeatured(); }
    public List<Hotel> searchByLocation(String location) { return hotelDAO.searchByLocation(location); }
    public List<Hotel> search(String query) { return hotelDAO.search(query); }
    public boolean update(Hotel hotel) { return hotelDAO.update(hotel); }
    public boolean delete(String id) { return hotelDAO.delete(id); }
    public long count() { return hotelDAO.count(); }
}

package com.travelbooking.service;

import com.travelbooking.dao.TrainDAO;
import com.travelbooking.model.Train;
import org.bson.types.ObjectId;
import java.util.List;

public class TrainService {

    private final TrainDAO trainDAO = new TrainDAO();

    public ObjectId create(Train train) { return trainDAO.insert(train); }
    public Train findById(String id) { return trainDAO.findById(id); }
    public List<Train> findAll() { return trainDAO.findAll(); }
    public List<Train> findAllIncludingInactive() { return trainDAO.findAllIncludingInactive(); }
    public List<Train> findFeatured() { return trainDAO.findFeatured(); }
    public List<Train> searchByOrigin(String origin) { return trainDAO.searchByOrigin(origin); }
    public List<Train> searchByDestination(String destination) { return trainDAO.searchByDestination(destination); }
    public List<Train> search(String query) { return trainDAO.search(query); }
    public boolean update(Train train) { return trainDAO.update(train); }
    public boolean delete(String id) { return trainDAO.delete(id); }
    public long count() { return trainDAO.count(); }
}

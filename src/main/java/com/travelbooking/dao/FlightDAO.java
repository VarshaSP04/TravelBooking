package com.travelbooking.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.travelbooking.model.Flight;
import com.travelbooking.util.MongoConnection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightDAO {

    private MongoCollection<Document> collection;

    public FlightDAO() {
        this.collection = MongoConnection.getDatabase().getCollection("flights");
    }

    private Document toDocument(Flight f) {
        return new Document()
                .append("flightNumber", f.getFlightNumber())
                .append("airline", f.getAirline())
                .append("origin", f.getOrigin())
                .append("destination", f.getDestination())
                .append("departureTime", f.getDepartureTime())
                .append("arrivalTime", f.getArrivalTime())
                .append("price", f.getPrice())
                .append("aircraft", f.getAircraft())
                .append("classes", f.getClasses())
                .append("image", f.getImage())
                .append("featured", f.isFeatured())
                .append("active", f.isActive())
                .append("createdAt", f.getCreatedAt())
                .append("updatedAt", f.getUpdatedAt());
    }

    @SuppressWarnings("unchecked")
    private Flight fromDocument(Document doc) {
        if (doc == null) return null;
        Flight f = new Flight();
        f.setId(doc.getObjectId("_id"));
        f.setFlightNumber(doc.getString("flightNumber"));
        f.setAirline(doc.getString("airline"));
        f.setOrigin(doc.getString("origin"));
        f.setDestination(doc.getString("destination"));
        f.setDepartureTime(doc.getDate("departureTime"));
        f.setArrivalTime(doc.getDate("arrivalTime"));
        f.setPrice(doc.getDouble("price") != null ? doc.getDouble("price") : 0);
        f.setAircraft(doc.getString("aircraft"));
        f.setClasses((List<String>) doc.get("classes"));
        f.setImage(doc.getString("image"));
        f.setFeatured(doc.getBoolean("featured", false));
        f.setActive(doc.getBoolean("active", true));
        f.setCreatedAt(doc.getDate("createdAt"));
        f.setUpdatedAt(doc.getDate("updatedAt"));
        return f;
    }

    public ObjectId insert(Flight flight) {
        Document doc = toDocument(flight);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public Flight findById(String id) {
        return fromDocument(collection.find(Filters.eq("_id", new ObjectId(id))).first());
    }

    public List<Flight> findAll() {
        List<Flight> list = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("active", true))
                .sort(Sorts.ascending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Flight> findAllIncludingInactive() {
        List<Flight> list = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.descending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Flight> findFeatured() {
        List<Flight> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(Filters.eq("featured", true), Filters.eq("active", true)))
                .limit(6)) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Flight> searchByOrigin(String origin) {
        List<Flight> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(
                        Filters.regex("origin", origin, "i"),
                        Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Flight> searchByDestination(String destination) {
        List<Flight> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(
                        Filters.regex("destination", destination, "i"),
                        Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Flight> search(String query) {
        List<Flight> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(
                        Filters.or(
                                Filters.regex("flightNumber", query, "i"),
                                Filters.regex("airline", query, "i"),
                                Filters.regex("origin", query, "i"),
                                Filters.regex("destination", query, "i")),
                        Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public boolean update(Flight flight) {
        return collection.updateOne(
                Filters.eq("_id", flight.getId()),
                Updates.combine(
                        Updates.set("flightNumber", flight.getFlightNumber()),
                        Updates.set("airline", flight.getAirline()),
                        Updates.set("origin", flight.getOrigin()),
                        Updates.set("destination", flight.getDestination()),
                        Updates.set("departureTime", flight.getDepartureTime()),
                        Updates.set("arrivalTime", flight.getArrivalTime()),
                        Updates.set("price", flight.getPrice()),
                        Updates.set("aircraft", flight.getAircraft()),
                        Updates.set("classes", flight.getClasses()),
                        Updates.set("image", flight.getImage()),
                        Updates.set("featured", flight.isFeatured()),
                        Updates.set("active", flight.isActive()),
                        Updates.set("updatedAt", new Date())
                )
        ).getModifiedCount() > 0;
    }

    public boolean delete(String id) {
        return collection.deleteOne(Filters.eq("_id", new ObjectId(id))).getDeletedCount() > 0;
    }

    public long count() {
        return collection.countDocuments(Filters.eq("active", true));
    }
}

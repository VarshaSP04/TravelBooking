package com.travelbooking.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.travelbooking.model.Trip;
import com.travelbooking.util.MongoConnection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TripDAO {

    private MongoCollection<Document> collection;

    public TripDAO() {
        this.collection = MongoConnection.getDatabase().getCollection("trips");
    }

    private Document toDocument(Trip t) {
        List<Document> itinDocs = t.getItinerary().stream()
                .map(i -> new Document("day", i.getDay())
                        .append("title", i.getTitle())
                        .append("description", i.getDescription()))
                .collect(Collectors.toList());

        return new Document()
                .append("name", t.getName())
                .append("description", t.getDescription())
                .append("destination", t.getDestination())
                .append("duration", t.getDuration())
                .append("price", t.getPrice())
                .append("maxGroupSize", t.getMaxGroupSize())
                .append("currentBookings", t.getCurrentBookings())
                .append("startDate", t.getStartDate())
                .append("endDate", t.getEndDate())
                .append("inclusions", t.getInclusions())
                .append("itinerary", itinDocs)
                .append("images", t.getImages())
                .append("category", t.getCategory())
                .append("featured", t.isFeatured())
                .append("active", t.isActive())
                .append("createdAt", t.getCreatedAt())
                .append("updatedAt", t.getUpdatedAt());
    }

    @SuppressWarnings("unchecked")
    private Trip fromDocument(Document doc) {
        if (doc == null) return null;
        Trip t = new Trip();
        t.setId(doc.getObjectId("_id"));
        t.setName(doc.getString("name"));
        t.setDescription(doc.getString("description"));
        t.setDestination(doc.getString("destination"));
        t.setDuration(doc.getInteger("duration", 0));
        t.setPrice(doc.getDouble("price") != null ? doc.getDouble("price") : 0);
        t.setMaxGroupSize(doc.getInteger("maxGroupSize", 0));
        t.setCurrentBookings(doc.getInteger("currentBookings", 0));
        t.setStartDate(doc.getDate("startDate"));
        t.setEndDate(doc.getDate("endDate"));
        t.setInclusions((List<String>) doc.get("inclusions"));
        t.setImages((List<String>) doc.get("images"));
        t.setCategory(doc.getString("category"));
        t.setFeatured(doc.getBoolean("featured", false));
        t.setActive(doc.getBoolean("active", true));
        t.setCreatedAt(doc.getDate("createdAt"));
        t.setUpdatedAt(doc.getDate("updatedAt"));

        List<Document> itinDocs = (List<Document>) doc.get("itinerary");
        if (itinDocs != null) {
            List<Trip.ItineraryDay> days = itinDocs.stream()
                    .map(d -> new Trip.ItineraryDay(
                            d.getInteger("day", 0),
                            d.getString("title"),
                            d.getString("description")))
                    .collect(Collectors.toList());
            t.setItinerary(days);
        }
        return t;
    }

    public ObjectId insert(Trip trip) {
        Document doc = toDocument(trip);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public Trip findById(String id) {
        return fromDocument(collection.find(Filters.eq("_id", new ObjectId(id))).first());
    }

    public List<Trip> findAll() {
        List<Trip> list = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("active", true))
                .sort(Sorts.ascending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Trip> findAllIncludingInactive() {
        List<Trip> list = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.descending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Trip> findFeatured() {
        List<Trip> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(Filters.eq("featured", true), Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Trip> findByCategory(String category) {
        List<Trip> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(Filters.eq("category", category), Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Trip> search(String query) {
        List<Trip> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(
                        Filters.or(
                                Filters.regex("name", query, "i"),
                                Filters.regex("destination", query, "i"),
                                Filters.regex("description", query, "i"),
                                Filters.regex("category", query, "i")),
                        Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public boolean update(Trip trip) {
        Document doc = toDocument(trip);
        doc.remove("createdAt");
        doc.append("updatedAt", new Date());
        return collection.replaceOne(
                Filters.eq("_id", trip.getId()), doc
        ).getModifiedCount() > 0;
    }

    public boolean incrementBookings(String id) {
        return collection.updateOne(
                Filters.eq("_id", new ObjectId(id)),
                Updates.inc("currentBookings", 1)
        ).getModifiedCount() > 0;
    }

    public boolean delete(String id) {
        return collection.deleteOne(Filters.eq("_id", new ObjectId(id))).getDeletedCount() > 0;
    }

    public long count() {
        return collection.countDocuments(Filters.eq("active", true));
    }
}

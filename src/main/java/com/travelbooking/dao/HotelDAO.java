package com.travelbooking.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.travelbooking.model.Hotel;
import com.travelbooking.util.MongoConnection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HotelDAO {

    private MongoCollection<Document> collection;

    public HotelDAO() {
        this.collection = MongoConnection.getDatabase().getCollection("hotels");
    }

    private Document toDocument(Hotel h) {
        List<Document> roomDocs = h.getRoomTypes().stream()
                .map(r -> new Document("type", r.getType())
                        .append("price", r.getPrice())
                        .append("available", r.getAvailable()))
                .collect(Collectors.toList());

        return new Document()
                .append("name", h.getName())
                .append("description", h.getDescription())
                .append("location", h.getLocation())
                .append("address", h.getAddress())
                .append("pricePerNight", h.getPricePerNight())
                .append("rating", h.getRating())
                .append("amenities", h.getAmenities())
                .append("images", h.getImages())
                .append("roomTypes", roomDocs)
                .append("featured", h.isFeatured())
                .append("active", h.isActive())
                .append("createdAt", h.getCreatedAt())
                .append("updatedAt", h.getUpdatedAt());
    }

    @SuppressWarnings("unchecked")
    private Hotel fromDocument(Document doc) {
        if (doc == null) return null;
        Hotel h = new Hotel();
        h.setId(doc.getObjectId("_id"));
        h.setName(doc.getString("name"));
        h.setDescription(doc.getString("description"));
        h.setLocation(doc.getString("location"));
        h.setAddress(doc.getString("address"));
        h.setPricePerNight(doc.getDouble("pricePerNight") != null ? doc.getDouble("pricePerNight") : 0);
        h.setRating(doc.getDouble("rating") != null ? doc.getDouble("rating") : 0);
        h.setAmenities((List<String>) doc.get("amenities"));
        h.setImages((List<String>) doc.get("images"));
        h.setFeatured(doc.getBoolean("featured", false));
        h.setActive(doc.getBoolean("active", true));
        h.setCreatedAt(doc.getDate("createdAt"));
        h.setUpdatedAt(doc.getDate("updatedAt"));

        // Parse room types
        List<Document> roomDocs = (List<Document>) doc.get("roomTypes");
        if (roomDocs != null) {
            List<Hotel.RoomType> rooms = roomDocs.stream()
                    .map(rd -> new Hotel.RoomType(
                            rd.getString("type"),
                            rd.getDouble("price") != null ? rd.getDouble("price") : 0,
                            rd.getInteger("available", 0)))
                    .collect(Collectors.toList());
            h.setRoomTypes(rooms);
        }
        return h;
    }

    public ObjectId insert(Hotel hotel) {
        Document doc = toDocument(hotel);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public Hotel findById(String id) {
        return fromDocument(collection.find(Filters.eq("_id", new ObjectId(id))).first());
    }

    public List<Hotel> findAll() {
        List<Hotel> list = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("active", true))
                .sort(Sorts.ascending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Hotel> findAllIncludingInactive() {
        List<Hotel> list = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.descending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Hotel> findFeatured() {
        List<Hotel> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(Filters.eq("featured", true), Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Hotel> searchByLocation(String location) {
        List<Hotel> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(
                        Filters.regex("location", location, "i"),
                        Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Hotel> search(String query) {
        List<Hotel> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(
                        Filters.or(
                                Filters.regex("name", query, "i"),
                                Filters.regex("location", query, "i"),
                                Filters.regex("description", query, "i")),
                        Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public boolean update(Hotel hotel) {
        return collection.updateOne(
                Filters.eq("_id", hotel.getId()),
                Updates.combine(
                        Updates.set("name", hotel.getName()),
                        Updates.set("description", hotel.getDescription()),
                        Updates.set("location", hotel.getLocation()),
                        Updates.set("address", hotel.getAddress()),
                        Updates.set("pricePerNight", hotel.getPricePerNight()),
                        Updates.set("rating", hotel.getRating()),
                        Updates.set("amenities", hotel.getAmenities()),
                        Updates.set("images", hotel.getImages()),
                        Updates.set("featured", hotel.isFeatured()),
                        Updates.set("active", hotel.isActive()),
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

package com.travelbooking.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.travelbooking.model.Booking;
import com.travelbooking.util.MongoConnection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDAO {

    private MongoCollection<Document> collection;

    public BookingDAO() {
        this.collection = MongoConnection.getDatabase().getCollection("bookings");
    }

    private Document toDocument(Booking b) {
        return new Document()
                .append("userId", b.getUserId())
                .append("bookingType", b.getBookingType())
                .append("referenceId", b.getReferenceId())
                .append("referenceName", b.getReferenceName())
                .append("checkIn", b.getCheckIn())
                .append("checkOut", b.getCheckOut())
                .append("guests", b.getGuests())
                .append("roomType", b.getRoomType())
                .append("totalPrice", b.getTotalPrice())
                .append("status", b.getStatus())
                .append("paymentStatus", b.getPaymentStatus())
                .append("specialRequests", b.getSpecialRequests())
                .append("origin", b.getOrigin())
                .append("destination", b.getDestination())
                .append("createdAt", b.getCreatedAt())
                .append("updatedAt", b.getUpdatedAt());
    }

    private Booking fromDocument(Document doc) {
        if (doc == null) return null;
        Booking b = new Booking();
        b.setId(doc.getObjectId("_id"));
        b.setUserId(doc.getObjectId("userId"));
        b.setBookingType(doc.getString("bookingType"));
        b.setReferenceId(doc.getObjectId("referenceId"));
        b.setReferenceName(doc.getString("referenceName"));
        b.setCheckIn(doc.getDate("checkIn"));
        b.setCheckOut(doc.getDate("checkOut"));
        b.setGuests(doc.getInteger("guests", 1));
        b.setRoomType(doc.getString("roomType"));
        b.setTotalPrice(doc.getDouble("totalPrice") != null ? doc.getDouble("totalPrice") : 0);
        b.setStatus(doc.getString("status"));
        b.setPaymentStatus(doc.getString("paymentStatus"));
        b.setSpecialRequests(doc.getString("specialRequests"));
        b.setOrigin(doc.getString("origin"));
        b.setDestination(doc.getString("destination"));
        b.setCreatedAt(doc.getDate("createdAt"));
        b.setUpdatedAt(doc.getDate("updatedAt"));
        return b;
    }

    public ObjectId insert(Booking booking) {
        Document doc = toDocument(booking);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public Booking findById(String id) {
        return fromDocument(collection.find(Filters.eq("_id", new ObjectId(id))).first());
    }

    public List<Booking> findByUserId(ObjectId userId) {
        List<Booking> list = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("userId", userId))
                .sort(Sorts.descending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Booking> findByUserId(String userId) {
        return findByUserId(new ObjectId(userId));
    }

    public List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.descending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public boolean updateStatus(String id, String status) {
        return collection.updateOne(
                Filters.eq("_id", new ObjectId(id)),
                Updates.combine(
                        Updates.set("status", status),
                        Updates.set("updatedAt", new Date())
                )
        ).getModifiedCount() > 0;
    }

    public boolean updatePaymentStatus(String id, String paymentStatus) {
        return collection.updateOne(
                Filters.eq("_id", new ObjectId(id)),
                Updates.combine(
                        Updates.set("paymentStatus", paymentStatus),
                        Updates.set("updatedAt", new Date())
                )
        ).getModifiedCount() > 0;
    }

    public boolean delete(String id) {
        return collection.deleteOne(Filters.eq("_id", new ObjectId(id))).getDeletedCount() > 0;
    }

    public long count() {
        return collection.countDocuments();
    }

    public long countByStatus(String status) {
        return collection.countDocuments(Filters.eq("status", status));
    }

    public double totalRevenue() {
        double total = 0;
        for (Document doc : collection.find(Filters.eq("paymentStatus", "PAID"))) {
            Double price = doc.getDouble("totalPrice");
            if (price != null) total += price;
        }
        return total;
    }
}

package com.travelbooking.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.travelbooking.model.Train;
import com.travelbooking.util.MongoConnection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainDAO {

    private MongoCollection<Document> collection;

    public TrainDAO() {
        this.collection = MongoConnection.getDatabase().getCollection("trains");
    }

    private Document toDocument(Train t) {
        return new Document()
                .append("trainNumber", t.getTrainNumber())
                .append("trainName", t.getTrainName())
                .append("origin", t.getOrigin())
                .append("destination", t.getDestination())
                .append("departureTime", t.getDepartureTime())
                .append("arrivalTime", t.getArrivalTime())
                .append("price", t.getPrice())
                .append("classes", t.getClasses())
                .append("image", t.getImage())
                .append("featured", t.isFeatured())
                .append("active", t.isActive())
                .append("createdAt", t.getCreatedAt())
                .append("updatedAt", t.getUpdatedAt());
    }

    @SuppressWarnings("unchecked")
    private Train fromDocument(Document doc) {
        if (doc == null) return null;
        Train t = new Train();
        t.setId(doc.getObjectId("_id"));
        t.setTrainNumber(doc.getString("trainNumber"));
        t.setTrainName(doc.getString("trainName"));
        t.setOrigin(doc.getString("origin"));
        t.setDestination(doc.getString("destination"));
        t.setDepartureTime(doc.getDate("departureTime"));
        t.setArrivalTime(doc.getDate("arrivalTime"));
        t.setPrice(doc.getDouble("price") != null ? doc.getDouble("price") : 0);
        t.setClasses((List<String>) doc.get("classes"));
        t.setImage(doc.getString("image"));
        t.setFeatured(doc.getBoolean("featured", false));
        t.setActive(doc.getBoolean("active", true));
        t.setCreatedAt(doc.getDate("createdAt"));
        t.setUpdatedAt(doc.getDate("updatedAt"));
        return t;
    }

    public ObjectId insert(Train train) {
        Document doc = toDocument(train);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public Train findById(String id) {
        return fromDocument(collection.find(Filters.eq("_id", new ObjectId(id))).first());
    }

    public List<Train> findAll() {
        List<Train> list = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("active", true))
                .sort(Sorts.descending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Train> findAllIncludingInactive() {
        List<Train> list = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.descending("createdAt"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Train> findFeatured() {
        List<Train> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(Filters.eq("featured", true), Filters.eq("active", true)))
                .limit(6)) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Train> searchByOrigin(String origin) {
        List<Train> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(
                        Filters.regex("origin", origin, "i"),
                        Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Train> searchByDestination(String destination) {
        List<Train> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(
                        Filters.regex("destination", destination, "i"),
                        Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public List<Train> search(String query) {
        List<Train> list = new ArrayList<>();
        for (Document doc : collection.find(
                Filters.and(
                        Filters.or(
                                Filters.regex("trainNumber", query, "i"),
                                Filters.regex("trainName", query, "i"),
                                Filters.regex("origin", query, "i"),
                                Filters.regex("destination", query, "i")),
                        Filters.eq("active", true)))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public boolean update(Train train) {
        return collection.updateOne(
                Filters.eq("_id", train.getId()),
                Updates.combine(
                        Updates.set("trainNumber", train.getTrainNumber()),
                        Updates.set("trainName", train.getTrainName()),
                        Updates.set("origin", train.getOrigin()),
                        Updates.set("destination", train.getDestination()),
                        Updates.set("departureTime", train.getDepartureTime()),
                        Updates.set("arrivalTime", train.getArrivalTime()),
                        Updates.set("price", train.getPrice()),
                        Updates.set("classes", train.getClasses()),
                        Updates.set("image", train.getImage()),
                        Updates.set("featured", train.isFeatured()),
                        Updates.set("active", train.isActive()),
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

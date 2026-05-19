package com.travelbooking.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.travelbooking.model.User;
import com.travelbooking.util.MongoConnection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO {

    private MongoCollection<Document> collection;

    public UserDAO() {
        this.collection = MongoConnection.getDatabase().getCollection("users");
    }

    private Document toDocument(User u) {
        return new Document()
                .append("firstName", u.getFirstName())
                .append("lastName", u.getLastName())
                .append("email", u.getEmail())
                .append("password", u.getPassword())
                .append("phone", u.getPhone())
                .append("address", u.getAddress())
                .append("profileImage", u.getProfileImage())
                .append("role", u.getRole())
                .append("createdAt", u.getCreatedAt())
                .append("updatedAt", u.getUpdatedAt());
    }

    private User fromDocument(Document doc) {
        if (doc == null) return null;
        User u = new User();
        u.setId(doc.getObjectId("_id"));
        u.setFirstName(doc.getString("firstName"));
        u.setLastName(doc.getString("lastName"));
        u.setEmail(doc.getString("email"));
        u.setPassword(doc.getString("password"));
        u.setPhone(doc.getString("phone"));
        u.setAddress(doc.getString("address"));
        u.setProfileImage(doc.getString("profileImage"));
        u.setRole(doc.getString("role"));
        u.setCreatedAt(doc.getDate("createdAt"));
        u.setUpdatedAt(doc.getDate("updatedAt"));
        return u;
    }

    public ObjectId insert(User user) {
        Document doc = toDocument(user);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public User findById(ObjectId id) {
        return fromDocument(collection.find(Filters.eq("_id", id)).first());
    }

    public User findById(String id) {
        return findById(new ObjectId(id));
    }

    public User findByEmail(String email) {
        return fromDocument(collection.find(Filters.eq("email", email)).first());
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            users.add(fromDocument(doc));
        }
        return users;
    }

    public long count() {
        return collection.countDocuments();
    }

    public boolean update(User user) {
        return collection.updateOne(
                Filters.eq("_id", user.getId()),
                Updates.combine(
                        Updates.set("firstName", user.getFirstName()),
                        Updates.set("lastName", user.getLastName()),
                        Updates.set("phone", user.getPhone()),
                        Updates.set("address", user.getAddress()),
                        Updates.set("profileImage", user.getProfileImage()),
                        Updates.set("updatedAt", new Date())
                )
        ).getModifiedCount() > 0;
    }

    public boolean updatePassword(ObjectId userId, String hashedPassword) {
        return collection.updateOne(
                Filters.eq("_id", userId),
                Updates.combine(
                        Updates.set("password", hashedPassword),
                        Updates.set("updatedAt", new Date())
                )
        ).getModifiedCount() > 0;
    }

    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    public boolean delete(String id) {
        return delete(new ObjectId(id));
    }

    public boolean emailExists(String email) {
        return collection.countDocuments(Filters.eq("email", email)) > 0;
    }
}

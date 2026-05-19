package com.travelbooking.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.travelbooking.model.Admin;
import com.travelbooking.util.MongoConnection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private MongoCollection<Document> collection;

    public AdminDAO() {
        this.collection = MongoConnection.getDatabase().getCollection("admins");
    }

    private Document toDocument(Admin a) {
        return new Document()
                .append("username", a.getUsername())
                .append("password", a.getPassword())
                .append("fullName", a.getFullName())
                .append("email", a.getEmail())
                .append("role", a.getRole())
                .append("createdAt", a.getCreatedAt());
    }

    private Admin fromDocument(Document doc) {
        if (doc == null) return null;
        Admin a = new Admin();
        a.setId(doc.getObjectId("_id"));
        a.setUsername(doc.getString("username"));
        a.setPassword(doc.getString("password"));
        a.setFullName(doc.getString("fullName"));
        a.setEmail(doc.getString("email"));
        a.setRole(doc.getString("role"));
        a.setCreatedAt(doc.getDate("createdAt"));
        return a;
    }

    public ObjectId insert(Admin admin) {
        Document doc = toDocument(admin);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public Admin findByUsername(String username) {
        return fromDocument(collection.find(Filters.eq("username", username)).first());
    }

    public Admin findById(String id) {
        return fromDocument(collection.find(Filters.eq("_id", new ObjectId(id))).first());
    }

    public List<Admin> findAll() {
        List<Admin> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public long count() {
        return collection.countDocuments();
    }
}

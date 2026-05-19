package com.travelbooking.model;

import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Hotel {
    private ObjectId id;
    private String name;
    private String description;
    private String location;
    private String address;
    private double pricePerNight;
    private double rating;
    private List<String> amenities;
    private List<String> images;
    private List<RoomType> roomTypes;
    private boolean featured;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;

    public Hotel() {
        this.amenities = new ArrayList<>();
        this.images = new ArrayList<>();
        this.roomTypes = new ArrayList<>();
        this.active = true;
        this.featured = false;
        this.rating = 0.0;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Inner class for room types
    public static class RoomType {
        private String type;
        private double price;
        private int available;

        public RoomType() {}
        public RoomType(String type, double price, int available) {
            this.type = type;
            this.price = price;
            this.available = available;
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public int getAvailable() { return available; }
        public void setAvailable(int available) { this.available = available; }
    }

    // Getters & Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public String getIdString() { return id != null ? id.toHexString() : null; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public List<RoomType> getRoomTypes() { return roomTypes; }
    public void setRoomTypes(List<RoomType> roomTypes) { this.roomTypes = roomTypes; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}

package com.travelbooking.model;

import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip {
    private ObjectId id;
    private String name;
    private String description;
    private String destination;
    private int duration; // days
    private double price;
    private int maxGroupSize;
    private int currentBookings;
    private Date startDate;
    private Date endDate;
    private List<String> inclusions;
    private List<ItineraryDay> itinerary;
    private List<String> images;
    private String category; // Adventure, Luxury, Cultural, Beach, Mountain
    private boolean featured;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;

    public Trip() {
        this.inclusions = new ArrayList<>();
        this.itinerary = new ArrayList<>();
        this.images = new ArrayList<>();
        this.active = true;
        this.featured = false;
        this.currentBookings = 0;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public static class ItineraryDay {
        private int day;
        private String title;
        private String description;

        public ItineraryDay() {}
        public ItineraryDay(int day, String title, String description) {
            this.day = day; this.title = title; this.description = description;
        }

        public int getDay() { return day; }
        public void setDay(int day) { this.day = day; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public String getIdString() { return id != null ? id.toHexString() : null; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getMaxGroupSize() { return maxGroupSize; }
    public void setMaxGroupSize(int maxGroupSize) { this.maxGroupSize = maxGroupSize; }

    public int getCurrentBookings() { return currentBookings; }
    public void setCurrentBookings(int currentBookings) { this.currentBookings = currentBookings; }

    public int getAvailableSlots() { return maxGroupSize - currentBookings; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public List<String> getInclusions() { return inclusions; }
    public void setInclusions(List<String> inclusions) { this.inclusions = inclusions; }

    public List<ItineraryDay> getItinerary() { return itinerary; }
    public void setItinerary(List<ItineraryDay> itinerary) { this.itinerary = itinerary; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}

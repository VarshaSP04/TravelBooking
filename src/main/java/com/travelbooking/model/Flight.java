package com.travelbooking.model;

import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Flight {
    private ObjectId id;
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private Date departureTime;
    private Date arrivalTime;
    private double price;
    private String aircraft;
    private List<String> classes;
    private String image;
    private boolean featured;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;

    public Flight() {
        this.classes = new ArrayList<>();
        this.active = true;
        this.featured = false;
        this.price = 0.0;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Getters & Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public String getIdString() { return id != null ? id.toHexString() : null; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Date getDepartureTime() { return departureTime; }
    public void setDepartureTime(Date departureTime) { this.departureTime = departureTime; }

    public Date getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(Date arrivalTime) { this.arrivalTime = arrivalTime; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getAircraft() { return aircraft; }
    public void setAircraft(String aircraft) { this.aircraft = aircraft; }

    public List<String> getClasses() { return classes; }
    public void setClasses(List<String> classes) { this.classes = classes; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}

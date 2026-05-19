package com.travelbooking.listener;

import com.travelbooking.service.AdminService;
import com.travelbooking.service.HotelService;
import com.travelbooking.service.TripService;
import com.travelbooking.service.FlightService;
import com.travelbooking.service.TrainService;
import com.travelbooking.model.Hotel;
import com.travelbooking.model.Trip;
import com.travelbooking.model.Flight;
import com.travelbooking.model.Train;
import com.travelbooking.util.MongoConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Seeds default admin and sample data on first startup.
 * Closes MongoDB connection on shutdown.
 */
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Travel Booking: Initializing ===");

        // Seed default admin
        try {
            new AdminService().ensureDefaultAdmin();
        } catch (Exception e) {
            System.err.println("Failed to seed default admin: " + e.getMessage());
        }

        // Seed sample data if collections are empty
        try {
            seedSampleData();
        } catch (Exception e) {
            System.err.println("Failed to seed sample data: " + e.getMessage());
        }

        System.out.println("=== Travel Booking: Ready ===");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MongoConnection.close();
        System.out.println("=== Travel Booking: Shutdown ===");
    }

    private void seedSampleData() {
        HotelService hotelService = new HotelService();
        TripService tripService = new TripService();
        FlightService flightService = new FlightService();
        TrainService trainService = new TrainService();

        // Build a set of existing hotel names so we only insert missing ones.
        Set<String> existingHotels = new HashSet<>();
        for (Hotel h : hotelService.findAllIncludingInactive()) {
            if (h.getName() != null) existingHotels.add(h.getName());
        }

        seedHotels(hotelService, existingHotels);

        Set<String> existingTrips = new HashSet<>();
        for (Trip t : tripService.findAllIncludingInactive()) {
            if (t.getName() != null) existingTrips.add(t.getName());
        }

        seedTrips(tripService, existingTrips);

        Set<String> existingFlights = new HashSet<>();
        for (Flight f : flightService.findAllIncludingInactive()) {
            if (f.getFlightNumber() != null) existingFlights.add(f.getFlightNumber());
        }

        seedFlights(flightService, existingFlights);

        Set<String> existingTrains = new HashSet<>();
        for (Train t : trainService.findAllIncludingInactive()) {
            if (t.getTrainNumber() != null) existingTrains.add(t.getTrainNumber());
        }

        seedTrains(trainService, existingTrains);
    }

    /* ---------- Helper methods ---------- */

    private void addHotel(HotelService svc, Set<String> existing,
                          String name, String description, String location, String address,
                          double pricePerNight, double rating, String imagePath,
                          List<String> amenities, List<Hotel.RoomType> roomTypes,
                          boolean featured) {
        if (existing.contains(name)) return;
        Hotel h = new Hotel();
        h.setName(name);
        h.setDescription(description);
        h.setLocation(location);
        h.setAddress(address);
        h.setPricePerNight(pricePerNight);
        h.setRating(rating);
        h.setAmenities(amenities);
        h.setImages(Arrays.asList(imagePath));
        h.setRoomTypes(roomTypes);
        h.setFeatured(featured);
        h.setActive(true);
        svc.create(h);
        existing.add(name);
    }

    private void addTrip(TripService svc, Set<String> existing,
                         String name, String description, String destination, int durationDays,
                         double price, int maxGroupSize, int startInMonths, String imagePath,
                         String category, boolean featured,
                         List<String> inclusions, List<Trip.ItineraryDay> itinerary) {
        if (existing.contains(name)) return;
        Trip t = new Trip();
        t.setName(name);
        t.setDescription(description);
        t.setDestination(destination);
        t.setDuration(durationDays);
        t.setPrice(price);
        t.setMaxGroupSize(maxGroupSize);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, startInMonths);
        t.setStartDate(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, durationDays);
        t.setEndDate(cal.getTime());
        t.setInclusions(inclusions);
        t.setItinerary(itinerary);
        t.setImages(Arrays.asList(imagePath));
        t.setCategory(category);
        t.setFeatured(featured);
        t.setActive(true);
        svc.create(t);
        existing.add(name);
    }

    private static Hotel.RoomType room(String type, double price, int available) {
        return new Hotel.RoomType(type, price, available);
    }

    private static Trip.ItineraryDay day(int n, String title, String desc) {
        return new Trip.ItineraryDay(n, title, desc);
    }

    /* ---------- Hotel catalog (25 hotels) ---------- */

    private void seedHotels(HotelService s, Set<String> ex) {
        // 1
        addHotel(s, ex, "Grand Palace Resort",
                "Luxury 5-star resort with breathtaking ocean views, infinity pool, and world-class spa. Perfect for couples and families seeking paradise.",
                "Maldives", "Ari Atoll, Maldives", 350.00, 4.9, "/images/hotels/hotel1.jpg",
                Arrays.asList("WiFi", "Pool", "Spa", "Restaurant", "Gym", "Beach Access", "Room Service"),
                Arrays.asList(room("Standard", 350.00, 20), room("Deluxe", 550.00, 10), room("Suite", 900.00, 5)), true);
        // 2
        addHotel(s, ex, "Alpine Lodge",
                "Cozy mountain retreat nestled in the Swiss Alps. Ski-in/ski-out access, panoramic views, and authentic Swiss hospitality.",
                "Swiss Alps", "Zermatt, Switzerland", 280.00, 4.7, "/images/hotels/hotel2.jpg",
                Arrays.asList("WiFi", "Fireplace", "Ski Storage", "Restaurant", "Hot Tub", "Mountain View"),
                Arrays.asList(room("Standard", 280.00, 15), room("Deluxe", 420.00, 8), room("Penthouse", 750.00, 3)), true);
        // 3
        addHotel(s, ex, "Sakura Garden Hotel",
                "Elegant Japanese-style hotel surrounded by cherry blossom gardens. Traditional onsen, tea ceremonies, and zen gardens.",
                "Kyoto, Japan", "Higashiyama Ward, Kyoto", 200.00, 4.8, "/images/hotels/hotel3.jpg",
                Arrays.asList("WiFi", "Onsen", "Tea Room", "Garden", "Restaurant", "Spa"),
                Arrays.asList(room("Standard", 200.00, 25), room("Superior", 320.00, 12), room("Imperial Suite", 600.00, 4)), true);
        // 4
        addHotel(s, ex, "Santorini Cliffside Villas",
                "Stunning whitewashed villas perched on Santorini cliffs with private pools and sunset views over the Aegean Sea.",
                "Santorini, Greece", "Oia, Santorini, Greece", 420.00, 4.9, "/images/hotels/hotel4.jpg",
                Arrays.asList("WiFi", "Private Pool", "Sea View", "Restaurant", "Bar", "Concierge"),
                Arrays.asList(room("Studio Villa", 420.00, 10), room("Honeymoon Suite", 650.00, 6), room("Grand Villa", 1100.00, 3)), true);
        // 5
        addHotel(s, ex, "The Royal London",
                "Iconic 5-star hotel in the heart of London with classic British elegance, afternoon tea, and views of Hyde Park.",
                "London, UK", "Mayfair, London W1, United Kingdom", 480.00, 4.8, "/images/hotels/hotel5.jpg",
                Arrays.asList("WiFi", "Concierge", "Restaurant", "Bar", "Gym", "Spa", "Butler Service"),
                Arrays.asList(room("Classic", 480.00, 18), room("Executive", 720.00, 10), room("Royal Suite", 1500.00, 4)), false);
        // 6
        addHotel(s, ex, "Manhattan Skyline Suites",
                "Modern luxury in the heart of Manhattan with floor-to-ceiling windows overlooking Central Park and Times Square.",
                "New York, USA", "Midtown Manhattan, New York, NY", 390.00, 4.6, "/images/hotels/hotel6.jpg",
                Arrays.asList("WiFi", "Gym", "Rooftop Bar", "Restaurant", "Business Center", "Valet Parking"),
                Arrays.asList(room("City View", 390.00, 22), room("Park View", 580.00, 12), room("Skyline Suite", 950.00, 5)), false);
        // 7
        addHotel(s, ex, "Dubai Marina Towers",
                "Ultra-modern 5-star hotel in Dubai Marina with private beach, infinity pool, and Burj Khalifa views.",
                "Dubai, UAE", "Dubai Marina, United Arab Emirates", 450.00, 4.8, "/images/hotels/hotel7.jpg",
                Arrays.asList("WiFi", "Private Beach", "Infinity Pool", "Spa", "5 Restaurants", "Kids Club", "Marina View"),
                Arrays.asList(room("Marina View", 450.00, 30), room("Sea View", 620.00, 18), room("Royal Suite", 1300.00, 6)), true);
        // 8
        addHotel(s, ex, "Tropical Bali Villas",
                "Private villas surrounded by tropical gardens and rice fields, each with its own pool and personal butler.",
                "Bali, Indonesia", "Ubud, Bali, Indonesia", 240.00, 4.7, "/images/hotels/hotel8.jpg",
                Arrays.asList("WiFi", "Private Pool", "Butler", "Spa", "Yoga Pavilion", "Restaurant"),
                Arrays.asList(room("Garden Villa", 240.00, 15), room("Pool Villa", 380.00, 10), room("Royal Villa", 720.00, 4)), false);
        // 9
        addHotel(s, ex, "Phuket Beach Resort",
                "Beachfront resort on Patong Beach with thai-style architecture, water sports, and authentic Thai cuisine.",
                "Phuket, Thailand", "Patong Beach, Phuket, Thailand", 180.00, 4.5, "/images/hotels/hotel9.jpg",
                Arrays.asList("WiFi", "Beach Access", "Pool", "Spa", "Water Sports", "Restaurant", "Bar"),
                Arrays.asList(room("Garden", 180.00, 28), room("Sea View", 280.00, 16), room("Beachfront Suite", 520.00, 6)), false);
        // 10
        addHotel(s, ex, "Goa Sunset Hotel",
                "Charming boutique hotel near Anjuna Beach with Portuguese colonial architecture and a relaxed beach vibe.",
                "Goa, India", "Anjuna, North Goa, India", 95.00, 4.4, "/images/hotels/hotel10.jpg",
                Arrays.asList("WiFi", "Pool", "Beach Shuttle", "Restaurant", "Bar", "Yoga"),
                Arrays.asList(room("Standard", 95.00, 24), room("Deluxe", 145.00, 14), room("Sea View Suite", 250.00, 6)), false);
        // 11
        addHotel(s, ex, "Taj Heritage Palace",
                "Restored 18th-century palace blending royal Rajasthani heritage with modern luxury. Stunning courtyards and frescoes.",
                "Jaipur, India", "City Palace Road, Jaipur, Rajasthan", 220.00, 4.9, "/images/hotels/hotel11.jpg",
                Arrays.asList("WiFi", "Pool", "Spa", "Heritage Tours", "Restaurant", "Bar", "Cultural Shows"),
                Arrays.asList(room("Heritage", 220.00, 18), room("Royal", 360.00, 10), room("Maharaja Suite", 780.00, 4)), true);
        // 12
        addHotel(s, ex, "Himalayan Pine Resort",
                "Mountain resort surrounded by pine forests with panoramic Himalayan views, adventure activities, and bonfire nights.",
                "Manali, India", "Old Manali, Himachal Pradesh", 110.00, 4.5, "/images/hotels/hotel12.jpg",
                Arrays.asList("WiFi", "Mountain View", "Bonfire", "Trekking", "Restaurant", "Heater"),
                Arrays.asList(room("Wood Cabin", 110.00, 20), room("Mountain Deluxe", 175.00, 10), room("Family Suite", 290.00, 5)), false);
        // 13
        addHotel(s, ex, "Paris Eiffel Boutique",
                "Charming boutique hotel steps from the Eiffel Tower with classic Parisian decor, balconies, and patisserie breakfasts.",
                "Paris, France", "7th Arrondissement, Paris, France", 320.00, 4.7, "/images/hotels/hotel13.jpg",
                Arrays.asList("WiFi", "Patisserie", "Concierge", "Eiffel View", "Bar", "Bicycle Rental"),
                Arrays.asList(room("Classic", 320.00, 16), room("Eiffel View", 480.00, 8), room("Balcony Suite", 720.00, 4)), false);
        // 14
        addHotel(s, ex, "Barcelona Beach Suites",
                "Stylish beachfront suites near La Barceloneta with rooftop pool, tapas bar, and easy access to Gothic Quarter.",
                "Barcelona, Spain", "La Barceloneta, Barcelona, Spain", 260.00, 4.6, "/images/hotels/hotel14.jpg",
                Arrays.asList("WiFi", "Rooftop Pool", "Beach Access", "Tapas Bar", "Gym", "City Tours"),
                Arrays.asList(room("City Suite", 260.00, 14), room("Sea View Suite", 390.00, 8), room("Penthouse", 680.00, 3)), false);
        // 15
        addHotel(s, ex, "Sydney Harbour Hotel",
                "Iconic harbour-front hotel with Opera House and Harbour Bridge views, sunset rooftop bar, and harbour cruises.",
                "Sydney, Australia", "Circular Quay, Sydney NSW", 360.00, 4.8, "/images/hotels/hotel15.jpg",
                Arrays.asList("WiFi", "Harbour View", "Rooftop Bar", "Pool", "Gym", "Cruise Pickup"),
                Arrays.asList(room("Harbour", 360.00, 18), room("Opera View", 540.00, 10), room("Bridge Suite", 980.00, 4)), false);
        // 16
        addHotel(s, ex, "Cape Town Vineyard Lodge",
                "Boutique lodge set among Cape Winelands vineyards with Table Mountain views, wine tastings, and farm-to-table dining.",
                "Cape Town, South Africa", "Stellenbosch, Cape Winelands", 210.00, 4.7, "/images/hotels/hotel16.jpg",
                Arrays.asList("WiFi", "Vineyard", "Wine Cellar", "Pool", "Restaurant", "Mountain View"),
                Arrays.asList(room("Vineyard", 210.00, 14), room("Mountain View", 320.00, 8), room("Estate Suite", 580.00, 4)), false);
        // 17
        addHotel(s, ex, "Rio Copacabana Resort",
                "Beachfront resort directly on Copacabana Beach with samba nights, caipirinha bar, and Sugarloaf Mountain views.",
                "Rio de Janeiro, Brazil", "Copacabana, Rio de Janeiro", 230.00, 4.5, "/images/hotels/hotel17.jpg",
                Arrays.asList("WiFi", "Beach Access", "Rooftop Pool", "Samba Night", "Bar", "Gym"),
                Arrays.asList(room("City View", 230.00, 22), room("Beach View", 360.00, 12), room("Copa Suite", 720.00, 5)), false);
        // 18
        addHotel(s, ex, "Cancun Paradise Resort",
                "All-inclusive Caribbean resort with white-sand beach, multiple pools, swim-up bars, and snorkeling tours.",
                "Cancun, Mexico", "Hotel Zone, Cancun, Quintana Roo", 290.00, 4.6, "/images/hotels/hotel18.jpg",
                Arrays.asList("WiFi", "All-Inclusive", "Pools", "Beach", "Snorkeling", "Spa", "Kids Club"),
                Arrays.asList(room("Garden View", 290.00, 28), room("Ocean View", 420.00, 16), room("Beachfront Suite", 760.00, 6)), false);
        // 19
        addHotel(s, ex, "Banff Mountain Lodge",
                "Rustic-luxury lodge in Banff National Park with hot springs, hiking trails, and grizzly-country wildlife tours.",
                "Banff, Canada", "Banff National Park, Alberta", 270.00, 4.7, "/images/hotels/hotel19.jpg",
                Arrays.asList("WiFi", "Hot Springs", "Mountain View", "Hiking", "Fireplace", "Restaurant"),
                Arrays.asList(room("Lodge Room", 270.00, 16), room("Mountain View", 410.00, 10), room("Cabin Suite", 720.00, 4)), false);
        // 20
        addHotel(s, ex, "Reykjavik Aurora Inn",
                "Cozy boutique inn in central Reykjavik with northern-lights wake-up service, geothermal spa, and glacier tours.",
                "Reykjavik, Iceland", "Laugavegur, Reykjavik 101", 250.00, 4.6, "/images/hotels/hotel20.jpg",
                Arrays.asList("WiFi", "Aurora Alert", "Geothermal Spa", "Restaurant", "Tour Desk"),
                Arrays.asList(room("Aurora", 250.00, 14), room("Skylight Loft", 380.00, 8), room("Penthouse Suite", 720.00, 3)), false);
        // 21
        addHotel(s, ex, "Marrakech Riad Palace",
                "Traditional riad with mosaic courtyards, rooftop terrace overlooking the Medina, and authentic Moroccan hammam.",
                "Marrakech, Morocco", "Medina, Marrakech, Morocco", 130.00, 4.7, "/images/hotels/hotel21.jpg",
                Arrays.asList("WiFi", "Hammam", "Rooftop Terrace", "Tagine Restaurant", "Pool", "Souk Tours"),
                Arrays.asList(room("Riad Room", 130.00, 14), room("Garden Suite", 220.00, 8), room("Royal Suite", 460.00, 3)), false);
        // 22
        addHotel(s, ex, "Hanoi Old Quarter Hotel",
                "Boutique hotel in the heart of the bustling Old Quarter, walking distance to Hoan Kiem Lake and street-food alleys.",
                "Hanoi, Vietnam", "Hoan Kiem District, Hanoi", 80.00, 4.5, "/images/hotels/hotel22.jpg",
                Arrays.asList("WiFi", "Restaurant", "Tour Desk", "Lake View", "Bar"),
                Arrays.asList(room("Standard", 80.00, 24), room("Deluxe", 130.00, 14), room("Lake View Suite", 240.00, 5)), false);
        // 23
        addHotel(s, ex, "Singapore Marina Bay Hotel",
                "Iconic skyscraper hotel with the world's largest infinity pool overlooking Marina Bay and the Singapore skyline.",
                "Singapore", "Marina Bay, Singapore", 510.00, 4.9, "/images/hotels/hotel23.jpg",
                Arrays.asList("WiFi", "Sky Pool", "Casino", "Spa", "Multiple Restaurants", "Shopping Mall"),
                Arrays.asList(room("Deluxe", 510.00, 30), room("Premier", 720.00, 18), room("Sky Suite", 1450.00, 6)), true);
        // 24
        addHotel(s, ex, "Istanbul Bosphorus Hotel",
                "Historic Ottoman-style hotel along the Bosphorus with views of two continents, Turkish bath, and rooftop dining.",
                "Istanbul, Turkey", "Beyoglu, Istanbul, Turkey", 200.00, 4.6, "/images/hotels/hotel24.jpg",
                Arrays.asList("WiFi", "Hammam", "Bosphorus View", "Rooftop Restaurant", "Spa", "Bar"),
                Arrays.asList(room("Classic", 200.00, 16), room("Bosphorus View", 320.00, 10), room("Sultan Suite", 680.00, 4)), false);
        // 25
        addHotel(s, ex, "Maui Ocean Resort",
                "Hawaiian beachfront resort with palm-fringed pools, snorkeling reefs, luau dinners, and volcano sunrise tours.",
                "Maui, Hawaii", "Wailea Beach, Maui, Hawaii", 410.00, 4.8, "/images/hotels/hotel25.jpg",
                Arrays.asList("WiFi", "Beach Access", "Pools", "Spa", "Luau", "Snorkeling", "Surf Lessons"),
                Arrays.asList(room("Garden View", 410.00, 22), room("Ocean View", 580.00, 12), room("Beachfront Suite", 1050.00, 5)), true);
    }

    /* ---------- Trip catalog (25 trips) ---------- */

    private void seedTrips(TripService s, Set<String> ex) {
        // 1
        addTrip(s, ex, "Bali Paradise Getaway",
                "7-day adventure through Bali's most stunning temples, rice terraces, beaches, and hidden waterfalls. Experience the magic of the Island of Gods.",
                "Bali, Indonesia", 7, 1299.00, 20, 1, "/images/trips/trip1.jpg", "Adventure", true,
                Arrays.asList("4-Star Hotel", "All Meals", "Transport", "English Guide", "Temple Entry"),
                Arrays.asList(
                        day(1, "Arrival & Welcome", "Airport pickup, hotel check-in, welcome dinner"),
                        day(2, "Temple Circuit", "Visit Uluwatu Temple & Tanah Lot at sunset"),
                        day(3, "Rice Terraces", "Explore Tegallalang rice terraces & coffee plantation"),
                        day(4, "Beach Day", "Snorkeling, surfing, and beach relaxation"),
                        day(5, "Waterfall Trek", "Hike to Sekumpul and Gitgit waterfalls"),
                        day(6, "Cultural Day", "Traditional dance, cooking class, art markets"),
                        day(7, "Departure", "Breakfast and airport transfer")));
        // 2
        addTrip(s, ex, "Northern Lights Explorer",
                "5-day magical journey to witness the Aurora Borealis in Iceland. Includes glacier walks, hot springs, and whale watching.",
                "Reykjavik, Iceland", 5, 2199.00, 15, 2, "/images/trips/trip2.jpg", "Adventure", true,
                Arrays.asList("Boutique Hotel", "Breakfast & Dinner", "4x4 Transport", "Expert Guide", "Gear"),
                Arrays.asList(
                        day(1, "Reykjavik Arrival", "City tour, welcome dinner"),
                        day(2, "Golden Circle", "Geysers, Gullfoss waterfall, Thingvellir"),
                        day(3, "Glacier Walk", "Solheimajokull glacier hiking"),
                        day(4, "Northern Lights Hunt", "Evening chase of the Aurora Borealis"),
                        day(5, "Blue Lagoon & Departure", "Morning at Blue Lagoon, departure")));
        // 3
        addTrip(s, ex, "Italian Dolce Vita",
                "10-day luxury tour through Rome, Florence, Venice, and the Amalfi Coast. Fine dining, art, history, and Mediterranean charm.",
                "Italy", 10, 3499.00, 12, 3, "/images/trips/trip3.jpg", "Luxury", true,
                Arrays.asList("5-Star Hotels", "All Meals", "Private Transport", "Museum Passes", "Wine Tastings"),
                Arrays.asList(
                        day(1, "Rome Arrival", "Colosseum & Roman Forum"),
                        day(2, "Vatican City", "Sistine Chapel & St. Peter's"),
                        day(3, "Tuscany Drive", "Chianti wine region"),
                        day(4, "Florence Art", "Uffizi Gallery & Ponte Vecchio"),
                        day(5, "Venice Canals", "Gondola ride & St. Mark's Square")));
        // 4
        addTrip(s, ex, "Safari & Serengeti",
                "6-day African safari through Tanzania's Serengeti. Witness the Great Migration, visit Ngorongoro Crater, and camp under the stars.",
                "Tanzania, Africa", 6, 2899.00, 10, 2, "/images/trips/trip4.jpg", "Adventure", true,
                Arrays.asList("Safari Lodge", "All Meals", "4x4 Safari Vehicle", "Expert Ranger", "Park Fees"),
                Arrays.asList(
                        day(1, "Arusha Arrival", "Welcome briefing and lodge check-in"),
                        day(2, "Serengeti Entry", "Full day game drive"),
                        day(3, "Great Migration", "Follow the wildebeest herds"),
                        day(4, "Ngorongoro Crater", "Crater floor game drive"),
                        day(5, "Maasai Village", "Cultural visit and sunset safari"),
                        day(6, "Departure", "Morning drive and airport transfer")));
        // 5
        addTrip(s, ex, "Kyoto Cultural Heritage",
                "6-day immersive cultural journey through Kyoto's ancient temples, tea ceremonies, geisha district, and zen gardens.",
                "Kyoto, Japan", 6, 1899.00, 14, 2, "/images/trips/trip5.jpg", "Cultural", true,
                Arrays.asList("Ryokan Stay", "Daily Breakfast", "Tea Ceremony", "Local Guide", "Temple Passes"),
                Arrays.asList(
                        day(1, "Arrival in Kyoto", "Welcome and traditional ryokan check-in"),
                        day(2, "Temple Tour", "Kinkaku-ji and Ryoan-ji zen garden"),
                        day(3, "Geisha District", "Walking tour of Gion and Pontocho"),
                        day(4, "Arashiyama", "Bamboo grove and monkey park"),
                        day(5, "Tea Ceremony", "Traditional tea ceremony and kimono experience"),
                        day(6, "Departure", "Final breakfast and farewell")));
        // 6
        addTrip(s, ex, "Maldives Beach Paradise",
                "5-day tropical escape to the Maldives. Overwater villas, crystal-clear lagoons, snorkeling with manta rays, and sunset cruises.",
                "Maldives", 5, 2799.00, 12, 1, "/images/trips/trip6.jpg", "Beach", true,
                Arrays.asList("Overwater Villa", "All Meals", "Speedboat Transfers", "Snorkel Gear", "Sunset Cruise"),
                Arrays.asList(
                        day(1, "Arrival", "Speedboat to resort, welcome dinner"),
                        day(2, "Reef Snorkel", "Coral reef snorkeling and lagoon swim"),
                        day(3, "Manta Ray Dive", "Snorkel with manta rays"),
                        day(4, "Sandbank Picnic", "Private sandbank lunch and sunset cruise"),
                        day(5, "Departure", "Final breakfast and transfer")));
        // 7
        addTrip(s, ex, "Egypt Pyramids & Nile",
                "8-day journey through ancient Egypt: pyramids of Giza, Sphinx, Luxor's Valley of the Kings, and a luxury Nile cruise.",
                "Egypt", 8, 2299.00, 16, 3, "/images/trips/trip7.jpg", "Cultural", false,
                Arrays.asList("4-Star Hotels", "Nile Cruise", "All Meals", "Egyptologist Guide", "Site Entries"),
                Arrays.asList(
                        day(1, "Cairo Arrival", "Welcome dinner overlooking the Pyramids"),
                        day(2, "Giza Plateau", "Pyramids, Sphinx, and Egyptian Museum"),
                        day(3, "Fly to Luxor", "Karnak and Luxor Temple"),
                        day(4, "Nile Cruise", "Board cruise, sail to Edfu"),
                        day(5, "Edfu & Kom Ombo", "Temple visits"),
                        day(6, "Aswan", "Philae Temple and Felucca ride"),
                        day(7, "Abu Simbel", "Day trip to Abu Simbel"),
                        day(8, "Departure", "Fly home")));
        // 8
        addTrip(s, ex, "New Zealand Adventure",
                "9-day adventure through New Zealand's South Island: bungee jumping in Queenstown, Milford Sound cruise, and Lord-of-the-Rings sites.",
                "New Zealand", 9, 3199.00, 12, 4, "/images/trips/trip8.jpg", "Adventure", false,
                Arrays.asList("3-4 Star Hotels", "Daily Breakfast", "Adventure Activities", "Transport", "Guide"),
                Arrays.asList(
                        day(1, "Auckland Arrival", "City tour and welcome dinner"),
                        day(2, "Hobbiton", "Lord of the Rings movie set tour"),
                        day(3, "Rotorua", "Geothermal park and Maori culture"),
                        day(4, "Fly to Queenstown", "Adventure capital welcome"),
                        day(5, "Bungee & Jet Boat", "Kawarau bungee and Shotover jet"),
                        day(6, "Milford Sound", "Fjord cruise"),
                        day(7, "Glenorchy", "Scenic drive and horseback ride"),
                        day(8, "Free Day", "Optional skydive or wine tour"),
                        day(9, "Departure", "Final breakfast and transfer")));
        // 9
        addTrip(s, ex, "Peru Machu Picchu Trek",
                "7-day classic Inca Trail trek to Machu Picchu, with Cusco exploration and Sacred Valley discoveries.",
                "Peru", 7, 1999.00, 14, 5, "/images/trips/trip9.jpg", "Adventure", false,
                Arrays.asList("Hotels & Camping", "All Meals on Trek", "Permits", "Porters", "Expert Guide"),
                Arrays.asList(
                        day(1, "Cusco Arrival", "Acclimatization and city tour"),
                        day(2, "Sacred Valley", "Pisac and Ollantaytambo ruins"),
                        day(3, "Trek Day 1", "Start Inca Trail to Wayllabamba"),
                        day(4, "Trek Day 2", "Dead Woman's Pass climb"),
                        day(5, "Trek Day 3", "Cloud forest descent to Winay Wayna"),
                        day(6, "Machu Picchu", "Sunrise at the lost city"),
                        day(7, "Departure", "Train back and fly home")));
        // 10
        addTrip(s, ex, "Thailand Island Hopping",
                "8-day tropical island adventure: Bangkok temples, Phi Phi snorkeling, Krabi cliffs, and Phuket beaches.",
                "Thailand", 8, 1499.00, 18, 2, "/images/trips/trip10.jpg", "Beach", false,
                Arrays.asList("Beach Resorts", "Daily Breakfast", "Speedboat Tours", "Snorkel Gear", "Transfers"),
                Arrays.asList(
                        day(1, "Bangkok", "Grand Palace and street food tour"),
                        day(2, "Fly to Phuket", "Beach relaxation"),
                        day(3, "Phi Phi Tour", "Maya Bay and snorkeling"),
                        day(4, "James Bond Island", "Phang Nga Bay tour"),
                        day(5, "Krabi", "Railay beach and rock climbing"),
                        day(6, "Four Islands", "Tup, Chicken, Poda, Phra Nang"),
                        day(7, "Free Day", "Spa and shopping"),
                        day(8, "Departure", "Transfer to airport")));
        // 11
        addTrip(s, ex, "Greek Islands Cruise",
                "7-day luxury yacht cruise through Mykonos, Santorini, Naxos, and Paros. Private beaches and Mediterranean cuisine.",
                "Greece", 7, 3899.00, 10, 3, "/images/trips/trip11.jpg", "Luxury", true,
                Arrays.asList("Private Yacht", "All Meals", "Drinks", "Crew", "Water Sports"),
                Arrays.asList(
                        day(1, "Athens Boarding", "Embark in Piraeus, sail at sunset"),
                        day(2, "Mykonos", "Old Town and Paradise Beach"),
                        day(3, "Naxos", "Ancient temples and beaches"),
                        day(4, "Paros", "Naoussa village and seafood"),
                        day(5, "Santorini", "Oia sunset and wineries"),
                        day(6, "Hydra", "Car-free island exploration"),
                        day(7, "Disembark", "Return to Athens")));
        // 12
        addTrip(s, ex, "Rajasthan Royal Tour",
                "9-day royal journey through Jaipur, Jodhpur, Udaipur, and Jaisalmer. Palaces, forts, camel safari, and lake views.",
                "Rajasthan, India", 9, 1399.00, 16, 2, "/images/trips/trip12.jpg", "Cultural", true,
                Arrays.asList("Heritage Hotels", "Breakfast & Dinner", "Private Car", "Local Guide", "Camel Safari"),
                Arrays.asList(
                        day(1, "Delhi Arrival", "Welcome and old Delhi tour"),
                        day(2, "Jaipur", "Amber Fort and City Palace"),
                        day(3, "Jaipur", "Hawa Mahal and shopping"),
                        day(4, "Jodhpur", "Mehrangarh Fort"),
                        day(5, "Jaisalmer", "Golden Fort"),
                        day(6, "Desert Safari", "Camel ride and dune dinner"),
                        day(7, "Udaipur", "City Palace and lake boat ride"),
                        day(8, "Udaipur", "Saheliyon ki Bari and shopping"),
                        day(9, "Departure", "Fly back to Delhi")));
        // 13
        addTrip(s, ex, "Kerala Backwaters",
                "5-day serene journey through God's Own Country: Munnar tea hills, Alleppey houseboat, and Kovalam beach.",
                "Kerala, India", 5, 799.00, 16, 1, "/images/trips/trip13.jpg", "Cultural", false,
                Arrays.asList("Resorts & Houseboat", "All Meals", "Private Car", "Ayurveda Session", "Boat Ride"),
                Arrays.asList(
                        day(1, "Cochin Arrival", "Fort Kochi heritage walk"),
                        day(2, "Munnar", "Tea plantations and Eravikulam Park"),
                        day(3, "Thekkady", "Spice gardens and Periyar wildlife"),
                        day(4, "Houseboat", "Backwater cruise in Alleppey"),
                        day(5, "Departure", "Cochin transfer")));
        // 14
        addTrip(s, ex, "Himalayan Trek Nepal",
                "10-day Annapurna Base Camp trek with Kathmandu cultural days. High passes, Sherpa villages, and Himalayan sunrises.",
                "Nepal", 10, 1599.00, 12, 3, "/images/trips/trip14.jpg", "Adventure", false,
                Arrays.asList("Tea Houses", "All Meals on Trek", "Permits", "Porters", "Sherpa Guide"),
                Arrays.asList(
                        day(1, "Kathmandu", "Welcome and Thamel walk"),
                        day(2, "Pokhara", "Drive to trek start"),
                        day(3, "Trek Begin", "Nayapul to Tikhedhunga"),
                        day(4, "Ghorepani", "Climb to Ghorepani"),
                        day(5, "Poon Hill", "Sunrise viewpoint"),
                        day(6, "Tadapani", "Forest trek"),
                        day(7, "Chhomrong", "Gurung village stay"),
                        day(8, "Base Camp", "Reach Annapurna Base Camp"),
                        day(9, "Descent", "Trek down to Bamboo"),
                        day(10, "Return", "Drive to Pokhara, fly to Kathmandu")));
        // 15
        addTrip(s, ex, "Australia Outback Adventure",
                "8-day adventure across the Australian Outback: Uluru sunrise, Kings Canyon, Great Barrier Reef, and Sydney finale.",
                "Australia", 8, 2799.00, 14, 4, "/images/trips/trip15.jpg", "Adventure", false,
                Arrays.asList("Hotels & Camping", "Breakfast & Dinner", "Internal Flights", "Reef Day Trip", "Guide"),
                Arrays.asList(
                        day(1, "Sydney", "Harbour and Opera House"),
                        day(2, "Fly to Uluru", "Sunset at the rock"),
                        day(3, "Uluru Sunrise", "Base walk and Kata Tjuta"),
                        day(4, "Kings Canyon", "Rim walk"),
                        day(5, "Fly to Cairns", "Rainforest exploration"),
                        day(6, "Reef Day", "Snorkel the Great Barrier Reef"),
                        day(7, "Free Day", "Optional skydive or scuba"),
                        day(8, "Departure", "Fly home")));
        // 16
        addTrip(s, ex, "Dubai Luxury Experience",
                "5-day ultra-luxury Dubai escape: Burj Khalifa, desert safari, Palm Jumeirah, and gold-souk shopping.",
                "Dubai, UAE", 5, 2499.00, 12, 2, "/images/trips/trip16.jpg", "Luxury", false,
                Arrays.asList("5-Star Hotel", "All Meals", "Private Driver", "Desert Safari", "Burj Tickets"),
                Arrays.asList(
                        day(1, "Arrival", "Burj Al Arab welcome and dinner"),
                        day(2, "City Tour", "Burj Khalifa and Dubai Mall"),
                        day(3, "Desert Safari", "Dune bashing and Bedouin camp"),
                        day(4, "Palm Jumeirah", "Atlantis Aquaventure"),
                        day(5, "Departure", "Gold souk shopping and transfer")));
        // 17
        addTrip(s, ex, "Caribbean Cruise",
                "7-day luxury Caribbean cruise visiting Bahamas, Jamaica, Cayman Islands, and Cozumel. White sand beaches and reef snorkeling.",
                "Caribbean", 7, 1899.00, 20, 3, "/images/trips/trip17.jpg", "Beach", false,
                Arrays.asList("Cruise Cabin", "All Meals", "Drinks Package", "Shore Excursions", "Entertainment"),
                Arrays.asList(
                        day(1, "Miami", "Embark and sail"),
                        day(2, "Bahamas", "Nassau beach day"),
                        day(3, "At Sea", "Pool and entertainment"),
                        day(4, "Jamaica", "Dunn's River Falls"),
                        day(5, "Cayman", "Stingray City"),
                        day(6, "Cozumel", "Mayan ruins and snorkel"),
                        day(7, "Disembark", "Return to Miami")));
        // 18
        addTrip(s, ex, "Patagonia Wilderness",
                "10-day trekking expedition through Torres del Paine and Los Glaciares: granite peaks, glaciers, and gauchos.",
                "Argentina & Chile", 10, 3299.00, 10, 5, "/images/trips/trip18.jpg", "Adventure", false,
                Arrays.asList("Lodges & Camping", "All Meals", "Park Fees", "Expert Guide", "Gear"),
                Arrays.asList(
                        day(1, "El Calafate", "Welcome dinner"),
                        day(2, "Perito Moreno", "Glacier trek"),
                        day(3, "El Chalten", "Drive to mountain town"),
                        day(4, "Fitz Roy", "Day hike to Laguna de los Tres"),
                        day(5, "Cerro Torre", "Day hike"),
                        day(6, "Cross Border", "Drive to Puerto Natales"),
                        day(7, "Torres del Paine", "Base of the towers"),
                        day(8, "French Valley", "W-trek section"),
                        day(9, "Grey Glacier", "Boat ride"),
                        day(10, "Departure", "Fly to Santiago")));
        // 19
        addTrip(s, ex, "Morocco Desert Safari",
                "7-day Moroccan adventure: Marrakech medina, Atlas Mountains, Sahara camel trek, and Fes blue city.",
                "Morocco", 7, 1399.00, 14, 2, "/images/trips/trip19.jpg", "Adventure", false,
                Arrays.asList("Riads & Desert Camp", "Breakfast & Dinner", "4x4 Transport", "Camel Trek", "Guide"),
                Arrays.asList(
                        day(1, "Marrakech", "Jemaa el-Fnaa night tour"),
                        day(2, "Atlas Mountains", "Berber village visit"),
                        day(3, "Ait Benhaddou", "Kasbah trail"),
                        day(4, "Sahara", "Camel ride to desert camp"),
                        day(5, "Sunrise", "Sunrise on dunes, drive to Fes"),
                        day(6, "Fes", "Medina tour and tannery"),
                        day(7, "Departure", "Fly home")));
        // 20
        addTrip(s, ex, "Vietnam Cultural Tour",
                "9-day journey through Vietnam: Hanoi old quarter, Ha Long Bay cruise, Hue imperial city, and Mekong Delta.",
                "Vietnam", 9, 1499.00, 16, 3, "/images/trips/trip20.jpg", "Cultural", false,
                Arrays.asList("3-4 Star Hotels", "Bay Cruise", "All Meals", "Domestic Flights", "Guide"),
                Arrays.asList(
                        day(1, "Hanoi", "Old quarter and water puppets"),
                        day(2, "Ha Long Bay", "Overnight cruise"),
                        day(3, "Bay Return", "Caves and kayaking"),
                        day(4, "Fly to Hue", "Imperial city tour"),
                        day(5, "Hoi An", "Ancient town and lanterns"),
                        day(6, "Cooking Class", "Local market and class"),
                        day(7, "Fly to Ho Chi Minh", "City tour and Cu Chi tunnels"),
                        day(8, "Mekong Delta", "Boat ride and floating market"),
                        day(9, "Departure", "Transfer to airport")));
        // 21
        addTrip(s, ex, "Switzerland Alpine Tour",
                "7-day scenic Alpine train journey: Zurich, Lucerne, Interlaken, Zermatt, and the Matterhorn. Glacier Express and Jungfrau.",
                "Switzerland", 7, 3199.00, 12, 4, "/images/trips/trip21.jpg", "Luxury", false,
                Arrays.asList("4-Star Hotels", "Breakfast", "Swiss Travel Pass", "Scenic Trains", "Cable Cars"),
                Arrays.asList(
                        day(1, "Zurich Arrival", "Old town walk"),
                        day(2, "Lucerne", "Chapel Bridge and Mt. Pilatus"),
                        day(3, "Interlaken", "Jungfraujoch top of Europe"),
                        day(4, "Glacier Express", "Train to Zermatt"),
                        day(5, "Matterhorn", "Gornergrat sunrise"),
                        day(6, "Geneva", "Lake cruise"),
                        day(7, "Departure", "Transfer to airport")));
        // 22
        addTrip(s, ex, "Spain Flamenco & Tapas",
                "8-day cultural tour of Spain: Madrid art, Toledo medieval, Cordoba mosque, Seville flamenco, and Granada Alhambra.",
                "Spain", 8, 1799.00, 16, 3, "/images/trips/trip22.jpg", "Cultural", false,
                Arrays.asList("Boutique Hotels", "Daily Breakfast", "Tapas Tour", "Flamenco Show", "Train Transfers"),
                Arrays.asList(
                        day(1, "Madrid", "Prado Museum"),
                        day(2, "Toledo", "Day trip and cathedral"),
                        day(3, "Cordoba", "Mezquita and Jewish Quarter"),
                        day(4, "Seville", "Real Alcazar and cathedral"),
                        day(5, "Flamenco Night", "Authentic show and tapas"),
                        day(6, "Granada", "Alhambra and Generalife"),
                        day(7, "Free Day", "Sacromonte caves"),
                        day(8, "Departure", "Fly home")));
        // 23
        addTrip(s, ex, "Hawaii Beach Escape",
                "6-day Hawaiian island getaway across Oahu and Maui: Waikiki, Pearl Harbor, Road to Hana, and Haleakala sunrise.",
                "Hawaii, USA", 6, 2299.00, 14, 2, "/images/trips/trip23.jpg", "Beach", false,
                Arrays.asList("Beach Resorts", "Inter-island Flight", "Daily Breakfast", "Tours", "Snorkel Gear"),
                Arrays.asList(
                        day(1, "Honolulu", "Waikiki and Diamond Head"),
                        day(2, "Pearl Harbor", "USS Arizona memorial"),
                        day(3, "Fly to Maui", "West Maui beaches"),
                        day(4, "Road to Hana", "Scenic drive and waterfalls"),
                        day(5, "Haleakala", "Sunrise summit"),
                        day(6, "Departure", "Lei farewell")));
        // 24
        addTrip(s, ex, "Norway Fjords Cruise",
                "7-day luxury fjord cruise from Bergen: Geirangerfjord, Sognefjord, Flam railway, and Nordic culture.",
                "Norway", 7, 2999.00, 12, 4, "/images/trips/trip24.jpg", "Luxury", false,
                Arrays.asList("Cruise Cabin", "All Meals", "Shore Excursions", "Flam Railway", "Bergen Tour"),
                Arrays.asList(
                        day(1, "Bergen", "Fish market and embarkation"),
                        day(2, "Hardanger", "Waterfalls and orchards"),
                        day(3, "Geirangerfjord", "Seven Sisters falls"),
                        day(4, "Alesund", "Art Nouveau town"),
                        day(5, "Sognefjord", "Flam railway scenic ride"),
                        day(6, "Stavanger", "Pulpit Rock hike"),
                        day(7, "Disembark", "Return to Bergen")));
        // 25
        addTrip(s, ex, "Phuket Beach Getaway",
                "5-day tropical Phuket escape: Patong nightlife, Phi Phi Islands, James Bond Island, and Thai cooking class.",
                "Phuket, Thailand", 5, 999.00, 16, 1, "/images/trips/trip25.jpg", "Beach", false,
                Arrays.asList("4-Star Resort", "Breakfast", "Speedboat Tours", "Cooking Class", "Transfers"),
                Arrays.asList(
                        day(1, "Arrival", "Welcome drink and resort check-in"),
                        day(2, "Phi Phi", "Island hopping and snorkeling"),
                        day(3, "James Bond", "Kayak and cave exploration"),
                        day(4, "Cooking Class", "Thai cuisine workshop"),
                        day(5, "Departure", "Beach farewell")));
    }

    private void seedFlights(FlightService svc, Set<String> existing) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 30);
        Date departure = cal.getTime();
        cal.add(Calendar.HOUR, 5);
        Date arrival = cal.getTime();

        addFlight(svc, existing, "AI101", "Air India", "Delhi", "Mumbai", departure, arrival, 150.00, "Airbus A320", Arrays.asList("Economy", "Business"), "/images/flights/flight1.jpg", true);
        addFlight(svc, existing, "AI202", "Air India", "Mumbai", "Delhi", departure, arrival, 150.00, "Airbus A320", Arrays.asList("Economy", "Business"), "/images/flights/flight2.jpg", false);
        addFlight(svc, existing, "6E301", "IndiGo", "Bangalore", "Delhi", departure, arrival, 120.00, "Airbus A320", Arrays.asList("Economy"), "/images/flights/flight3.jpg", true);
        addFlight(svc, existing, "6E402", "IndiGo", "Delhi", "Bangalore", departure, arrival, 120.00, "Airbus A320", Arrays.asList("Economy"), "/images/flights/flight4.jpg", false);
        addFlight(svc, existing, "UK501", "Vistara", "Mumbai", "Bangalore", departure, arrival, 180.00, "Boeing 737", Arrays.asList("Economy", "Premium", "Business"), "/images/flights/flight5.jpg", true);
        addFlight(svc, existing, "UK602", "Vistara", "Bangalore", "Mumbai", departure, arrival, 180.00, "Boeing 737", Arrays.asList("Economy", "Premium", "Business"), "/images/flights/flight6.jpg", false);
        addFlight(svc, existing, "SG701", "SpiceJet", "Chennai", "Delhi", departure, arrival, 130.00, "Boeing 737", Arrays.asList("Economy"), "/images/flights/flight7.jpg", true);
        addFlight(svc, existing, "SG802", "SpiceJet", "Delhi", "Chennai", departure, arrival, 130.00, "Boeing 737", Arrays.asList("Economy"), "/images/flights/flight8.jpg", false);
        addFlight(svc, existing, "G8901", "GoAir", "Hyderabad", "Mumbai", departure, arrival, 140.00, "Airbus A320", Arrays.asList("Economy"), "/images/flights/flight9.jpg", true);
        addFlight(svc, existing, "G8102", "GoAir", "Mumbai", "Hyderabad", departure, arrival, 140.00, "Airbus A320", Arrays.asList("Economy"), "/images/flights/flight10.jpg", false);

        // International flights
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date intDep = cal.getTime();
        cal.add(Calendar.HOUR, 10);
        Date intArr = cal.getTime();

        addFlight(svc, existing, "EK501", "Emirates", "Dubai", "Delhi", intDep, intArr, 450.00, "Boeing 777", Arrays.asList("Economy", "Business", "First"), "/images/flights/flight11.jpg", true);
        addFlight(svc, existing, "EK502", "Emirates", "Delhi", "Dubai", intDep, intArr, 450.00, "Boeing 777", Arrays.asList("Economy", "Business", "First"), "/images/flights/flight12.jpg", false);
        addFlight(svc, existing, "BA101", "British Airways", "London", "Mumbai", intDep, intArr, 620.00, "Boeing 787", Arrays.asList("Economy", "Premium", "Business", "First"), "/images/flights/flight13.jpg", true);
        addFlight(svc, existing, "BA102", "British Airways", "Mumbai", "London", intDep, intArr, 620.00, "Boeing 787", Arrays.asList("Economy", "Premium", "Business", "First"), "/images/flights/flight14.jpg", false);
        addFlight(svc, existing, "SQ301", "Singapore Airlines", "Singapore", "Delhi", intDep, intArr, 520.00, "Airbus A350", Arrays.asList("Economy", "Premium", "Business", "First"), "/images/flights/flight15.jpg", true);
        addFlight(svc, existing, "SQ302", "Singapore Airlines", "Delhi", "Singapore", intDep, intArr, 520.00, "Airbus A350", Arrays.asList("Economy", "Premium", "Business", "First"), "/images/flights/flight16.jpg", false);
        addFlight(svc, existing, "LH601", "Lufthansa", "Frankfurt", "Bangalore", intDep, intArr, 580.00, "Airbus A380", Arrays.asList("Economy", "Premium", "Business", "First"), "/images/flights/flight17.jpg", true);
        addFlight(svc, existing, "LH602", "Lufthansa", "Bangalore", "Frankfurt", intDep, intArr, 580.00, "Airbus A380", Arrays.asList("Economy", "Premium", "Business", "First"), "/images/flights/flight18.jpg", false);
        addFlight(svc, existing, "TG401", "Thai Airways", "Bangkok", "Delhi", intDep, intArr, 380.00, "Boeing 777", Arrays.asList("Economy", "Business"), "/images/flights/flight19.jpg", true);
        addFlight(svc, existing, "TG402", "Thai Airways", "Delhi", "Bangkok", intDep, intArr, 380.00, "Boeing 777", Arrays.asList("Economy", "Business"), "/images/flights/flight20.jpg", false);
    }

    private void seedTrains(TrainService svc, Set<String> existing) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_MONTH, 30);
    Date departure = cal.getTime();
    cal.add(Calendar.HOUR, 16);
    Date arrival = cal.getTime();

    addTrain(svc, existing, "12001", "Bhopal Express", "New Delhi", "Bhopal", departure, arrival, 50.00, Arrays.asList("Sleeper", "3AC", "2AC", "1A"), "/images/trains/train1.jpg", true);
    addTrain(svc, existing, "12002", "Bhopal Express", "Bhopal", "New Delhi", departure, arrival, 50.00, Arrays.asList("Sleeper", "3AC", "2AC", "1A"), "/images/trains/train2.jpg", false);
    addTrain(svc, existing, "12259", "Sealdah Duronto", "New Delhi", "Howrah", departure, arrival, 120.00, Arrays.asList("3AC", "2AC", "1A"), "/images/trains/train3.jpg", true);
    addTrain(svc, existing, "12260", "Sealdah Duronto", "Howrah", "New Delhi", departure, arrival, 120.00, Arrays.asList("3AC", "2AC", "1A"), "/images/trains/train4.jpg", false);
    addTrain(svc, existing, "12301", "Rajdhani Express", "New Delhi", "Howrah", departure, arrival, 150.00, Arrays.asList("3AC", "2AC", "1A"), "/images/trains/train5.jpg", true);
    addTrain(svc, existing, "12302", "Rajdhani Express", "Howrah", "New Delhi", departure, arrival, 150.00, Arrays.asList("3AC", "2AC", "1A"), "/images/trains/train6.jpg", false);
    addTrain(svc, existing, "12627", "Karnataka Express", "New Delhi", "Bangalore", departure, arrival, 80.00, Arrays.asList("Sleeper", "3AC", "2AC", "1A"), "/images/trains/train7.jpg", true);
    addTrain(svc, existing, "12628", "Karnataka Express", "Bangalore", "New Delhi", departure, arrival, 80.00, Arrays.asList("Sleeper", "3AC", "2AC", "1A"), "/images/trains/train8.jpg", false);
    addTrain(svc, existing, "12951", "Mumbai Rajdhani", "Mumbai", "New Delhi", departure, arrival, 130.00, Arrays.asList("3AC", "2AC", "1A"), "/images/trains/train9.jpg", true);
    addTrain(svc, existing, "12952", "Mumbai Rajdhani", "New Delhi", "Mumbai", departure, arrival, 130.00, Arrays.asList("3AC", "2AC", "1A"), "/images/trains/train10.jpg", false);
}

private void addFlight(FlightService svc, Set<String> existing, String flightNumber, String airline,
                      String origin, String destination, Date departureTime, Date arrivalTime,
                      double price, String aircraft, List<String> classes, String image, boolean featured) {
    if (existing.contains(flightNumber)) return;
    Flight f = new Flight();
    f.setFlightNumber(flightNumber);
    f.setAirline(airline);
    f.setOrigin(origin);
    f.setDestination(destination);
    f.setDepartureTime(departureTime);
    f.setArrivalTime(arrivalTime);
    f.setPrice(price);
    f.setAircraft(aircraft);
    f.setClasses(classes);
    f.setImage(image);
    f.setFeatured(featured);
    f.setActive(true);
    svc.create(f);
    existing.add(flightNumber);
}

private void addTrain(TrainService svc, Set<String> existing, String trainNumber, String trainName,
                     String origin, String destination, Date departureTime, Date arrivalTime,
                     double price, List<String> classes, String image, boolean featured) {
    if (existing.contains(trainNumber)) return;
    Train t = new Train();
    t.setTrainNumber(trainNumber);
    t.setTrainName(trainName);
    t.setOrigin(origin);
    t.setDestination(destination);
    t.setDepartureTime(departureTime);
    t.setArrivalTime(arrivalTime);
    t.setPrice(price);
    t.setClasses(classes);
    t.setImage(image);
    t.setFeatured(featured);
    t.setActive(true);
    svc.create(t);
    existing.add(trainNumber);
    }
}

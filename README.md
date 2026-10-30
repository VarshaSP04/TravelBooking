# ✈️ TravelBooking - Full-Stack Travel & Hospitality Platform

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10%20(Servlet%206.0)-red.svg)](https://jakarta.ee/)
[![Apache Tomcat](https://img.shields.io/badge/Apache%20Tomcat-10.1-yellow.svg)](https://tomcat.apache.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-5.2%20Driver-green.svg)](https://www.mongodb.com/)
[![Maven](https://img.shields.io/badge/Apache%20Maven-3.9-blue.svg)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED.svg)](https://www.docker.com/)

A modern, full-stack travel booking and tourism management platform built with **Java 17**, **Jakarta EE 10 (Servlets & JSP/JSTL)**, and **MongoDB**. **TravelBooking** delivers an end-to-end booking experience across vacation packages, hotels, flights, and trains, complemented by an administrative control panel for inventory and reservation management.

---

## 🌟 Key Features

### 👤 Customer Experience
- **Multi-Category Booking Engine**:
  - **Vacation Packages & Guided Trips**: Detailed itineraries, included/excluded perks, difficulty levels, and group reservations.
  - **Hotels & Accommodations**: Search across destinations, filter by amenities (Wi-Fi, Pool, Spa, etc.), choose room types, and reserve dates.
  - **Flights**: Browse multi-class air travel, search by origin/destination, and book flight tickets.
  - **Trains**: Search railway routes, class categories, departure times, and reserve seats.
- **Global Search & Filter**: Real-time filtering across destinations, price ranges, travel categories, ratings, and availability.
- **Instant Booking Confirmation**: Automatically generates a unique alphanumeric booking reference code with passenger and reservation summaries.
- **Personalized User Dashboard**:
  - Manage upcoming, completed, and cancelled reservations.
  - Profile settings to update contact information and change passwords securely.
- **Robust Security**:
  - Password encryption using **BCrypt** (`jbcrypt`).
  - Session-based state management with automatic 30-minute inactivity timeouts.
  - Granular servlet filter route protection.

### 🛡️ Back-Office Admin Portal
- **Operations Dashboard**: Real-time business metrics including total active bookings, revenue calculations, user statistics, and inventory counts.
- **Inventory Management**:
  - Add, update, toggle active status, or remove vacation trips and hotels.
  - Rich metadata support: photo URLs, itineraries, amenities, and room pricing.
- **Reservation Oversight**: View all customer bookings across services, review customer details, and update booking statuses (`CONFIRMED`, `PENDING`, `CANCELLED`).
- **User Directory**: View registered customer accounts and roles.

### ⚙️ Developer & System Features
- **Auto-Seeding Mechanism**: Automatically populates sample trips, hotels, flights, and trains, and creates the default Super Admin on first startup.
- **Containerized Deployment**: Multi-stage Docker build producing a lean Tomcat 10.1 container.
- **Zero-Config Local Run**: Maven Cargo plugin downloads, configures, and serves the application on Tomcat 10.1 with a single command.

---

## 🛠️ Technology Stack

| Layer | Technology | Details |
|---|---|---|
| **Language & Platform** | Java 17 (LTS) | Modern Java features, robust performance |
| **Web Framework** | Jakarta EE 10 | Servlet API 6.0, JSP 3.1, JSTL 3.0 |
| **Database** | MongoDB | MongoDB Java Sync Driver 5.2.0, BSON ObjectIds |
| **Security** | BCrypt & Jakarta Filters | Password salting & hashing via `jbcrypt`, role-based servlet filters |
| **Serialization** | Google Gson | JSON serialization for dynamic responses |
| **Application Server** | Apache Tomcat 10.1 | Embedded Cargo container / Standalone / Docker |
| **Build & Dependency Tool** | Apache Maven | Packaging as standard `.war` |
| **Frontend** | Vanilla HTML5 / Modern CSS3 / JS | Clean design system, glassmorphism, responsive grid & micro-animations |

---

## 📂 Project Architecture

```
TravelBooking/
├── Dockerfile                          # Multi-stage Docker configuration (Maven + Tomcat 10.1)
├── pom.xml                             # Maven build definition, dependencies & Cargo plugin
└── src/
    └── main/
        ├── java/com/travelbooking/
        │   ├── controller/             # Jakarta HTTP Servlets
        │   │   ├── admin/              # Admin dashboard, inventory & booking management servlets
        │   │   ├── AuthServlet.java    # Customer login
        │   │   ├── BookingServlet.java # Multi-service reservation handler
        │   │   ├── HomeServlet.java    # Homepage & featured discovery
        │   │   ├── HotelServlet.java   # Hotel listing & details
        │   │   ├── TripServlet.java    # Vacation packages & itinerary details
        │   │   ├── FlightServlet.java  # Flight schedules & booking
        │   │   ├── TrainServlet.java   # Railway schedules & booking
        │   │   └── ...
        │   ├── dao/                    # MongoDB Data Access Objects (CRUD operations)
        │   ├── filter/                 # AuthFilter & AdminAuthFilter route guards
        │   ├── listener/               # AppStartupListener (lifecycle & database auto-seeder)
        │   ├── model/                  # Data models (User, Admin, Booking, Hotel, Trip, Flight, Train)
        │   ├── service/                # Business logic layer
        │   └── util/                   # MongoConnection manager, PasswordUtil, ConfigLoader
        ├── resources/
        │   └── config.properties       # Database credentials and app properties
        └── webapp/
            ├── WEB-INF/
            │   ├── views/              # JSP templates (user-facing and admin portal)
            │   └── web.xml             # Deployment descriptor (servlets, filters, session timeouts)
            ├── css/                    # Modular CSS design system (components, layout, animations)
            ├── js/                     # Client-side validation, UI interactions & modals
            └── images/                 # Static graphical assets
```

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed on your machine:
- **Java Development Kit (JDK) 17** or later: `java -version`
- **Apache Maven 3.8+**: `mvn -version`
- **MongoDB** (Local instance running on `localhost:27017` OR a [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) cluster URL)
- *(Optional)* **Docker**: `docker --version`

---

### 1. Configure MongoDB Connection

Edit the configuration file at `TravelBooking/src/main/resources/config.properties`:

```properties
# Option A: MongoDB Atlas Cloud (recommended)
mongodb.uri=mongodb+srv://<username>:<password>@cluster0.example.mongodb.net/?appName=Cluster0

# Option B: Local MongoDB Instance (fallback when mongodb.uri is empty)
mongodb.host=localhost
mongodb.port=27017
mongodb.database=travelbooking

# Application Settings
app.name=Travel Booking
app.version=1.0.0
app.upload.path=/uploads
```

> **Note**: If using a connection URI with special characters in your password, ensure they are URL-encoded (e.g. `@` -> `%40`, `$` -> `%24`).

---

### 2. Run the Application

You can run the project using either **Maven Cargo**, **Docker**, or an **External Tomcat Server**.

#### Option A: 1-Click Launch (Windows Local Setup)

Double-click or run [`start-all.bat`](file:///c:/Users/User/Desktop/Project/start-all.bat) from the project root. It automatically starts local MongoDB, launches Apache Tomcat 10, and opens your browser:

```bat
start-all.bat
```

To stop all services later, simply run [`stop-all.bat`](file:///c:/Users/User/Desktop/Project/stop-all.bat).

---

#### Option B: Run via Maven Cargo (No manual Tomcat install needed)

The project is pre-configured with the `cargo-maven3-plugin`, which automatically downloads Tomcat 10.1, deploys the WAR, and launches the server:

```bash
cd TravelBooking
mvn clean package cargo:run
```

Once running, navigate to:
👉 **[http://localhost:8080/TravelBooking](http://localhost:8080/TravelBooking)**

---

#### Option B: Run with Docker

Build and run the containerized image:

```bash
cd TravelBooking

# Build Docker image
docker build -t travelbooking:latest .

# Run container on port 8080
docker run -d -p 8080:8080 --name travelbooking-app travelbooking:latest
```

The application will be deployed as the root application:
👉 **[http://localhost:8080/](http://localhost:8080/)**

---

#### Option C: Manual WAR Deployment

1. Package the WAR file:
   ```bash
   cd TravelBooking
   mvn clean package
   ```
2. Copy `target/TravelBooking.war` into your Tomcat 10.1 `webapps/` directory.
3. Start Tomcat:
   - **Linux / macOS**: `./bin/startup.sh`
   - **Windows**: `.\bin\startup.bat`
4. Access at `http://localhost:8080/TravelBooking`.

---

## 🔑 Default Credentials & Data Seeding

On the initial application startup, `AppStartupListener` checks MongoDB and automatically seeds default accounts and sample catalog entries.

### Admin Access
- **Admin Portal URL**: `http://localhost:8080/TravelBooking/admin/login` (or `/admin/login`)
- **Username**: `admin`
- **Password**: `admin123`

### Sample User Access
- You can register a new account on the `/register` page or test with any created user.
- Passwords are encrypted with BCrypt upon creation.

### Manual Seed Trigger
If you ever reset or clean your MongoDB database and wish to re-populate sample hotels, trips, flights, and trains:
- Visit: `http://localhost:8080/TravelBooking/seed-data`

---

## 🗺️ Key Application Routes

### Public Endpoints
| Route | Description |
|---|---|
| `/home` | Landing page featuring top destinations, search bar, and featured packages |
| `/trips`, `/trip-detail?id=...` | Browse vacation packages and detailed daily itineraries |
| `/hotels`, `/hotel-detail?id=...` | Hotel discovery, amenities overview, and room types |
| `/flights`, `/flight-detail?id=...`| Flight schedules, airlines, and seat pricing |
| `/trains`, `/train-detail?id=...` | Railway schedules, stations, and coach categories |
| `/search` | Multi-category filtered search results |
| `/login`, `/register`, `/logout` | Customer authentication workflows |

### Protected User Endpoints (`/dashboard/*`, `/booking/*`, `/profile/*`)
| Route | Description |
|---|---|
| `/booking` | Complete reservation for any selected hotel, trip, flight, or train |
| `/dashboard` | Customer dashboard overview and recent bookings |
| `/booking-history` | View, track, or cancel existing reservations |
| `/profile` | Manage contact details and change account passwords |

### Protected Admin Endpoints (`/admin/*`)
| Route | Description |
|---|---|
| `/admin/login` | Administrative login portal |
| `/admin/dashboard` | Key performance indicators, metrics, and recent orders |
| `/admin/trips` | Add, edit, toggle visibility, and delete vacation packages |
| `/admin/hotels` | Manage hotels, room inventory, pricing, and descriptions |
| `/admin/bookings` | Manage customer bookings and approve or update statuses |
| `/admin/users` | Overview of registered user profiles |

---

## 🔒 Security Best Practices

1. **Authentication Gateways**: All protected user and admin endpoints are guarded by Servlet Filters (`AuthFilter` and `AdminAuthFilter`), redirecting unauthorized requests.
2. **Password Protection**: Passwords are never stored in plaintext; salted and hashed via BCrypt before writing to MongoDB.
3. **Session Hardening**: Sessions enforce a 30-minute expiration period. Logging out invalidates the session and purges cached context.
4. **Environment Variables**: For production deployments, replace hardcoded MongoDB URI in `config.properties` or inject connection variables via Docker environment flags.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!
1. Fork the Project.
2. Create your Feature Branch (`git checkout -b feature/NewFeature`).
3. Commit your Changes (`git commit -m 'Add NewFeature'`).
4. Push to the Branch (`git push origin feature/NewFeature`).
5. Open a Pull Request.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

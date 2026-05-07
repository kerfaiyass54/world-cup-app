
# World Cup Analytics Platform 🏆📊

![GitHub Stars](https://img.shields.io/github/stars/kerfaiyass54/world-cup-app?style=for-the-badge)
![GitHub Forks](https://img.shields.io/github/forks/kerfaiyass54/world-cup-app?style=for-the-badge)
![GitHub Issues](https://img.shields.io/github/issues/kerfaiyass54/world-cup-app?style=for-the-badge)
![License](https://img.shields.io/github/license/kerfaiyass54/world-cup-app?style=for-the-badge)

**A comprehensive World Cup analytics platform that combines data science, real-time processing, and interactive visualization to provide deep insights into football history and trends.**

---

## ✨ Features

✅ **Multi-layered Analytics Pipeline**
- Data processing with Python (Pandas, NumPy)
- Real-time Kafka streaming for analytics
- Spring Boot backend for API services

✅ **Interactive Dashboard**
- Angular frontend with Chart.js visualizations
- Responsive design with Bootstrap 5
- AI-powered chat interface for insights

✅ **Comprehensive World Cup Statistics**
- Champion analysis across eras
- Host nation performance metrics
- Scoring trends and patterns
- Tournament structure evolution

✅ **Modern Architecture**
- Microservices approach with Spring Boot
- Dockerized environment for easy deployment
- PostgreSQL database for persistent storage
- Kafka for real-time data processing

---

## 🛠️ Tech Stack

**Backend:**
- Java 21
- Spring Boot 4.0.5
- Spring Data JPA
- Spring Kafka
- PostgreSQL 15
- Kafka Streams

**Analytics:**
- Python 3.11
- Pandas
- NumPy
- Kafka Python Client

**Frontend:**
- Angular 21.2
- TypeScript
- Chart.js
- Bootstrap 5
- Country Flag Icons

**DevOps:**
- Docker
- Docker Compose
- Maven
- Git

---

## 📦 Installation

### Prerequisites

Before you begin, ensure you have the following installed:
- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [Node.js](https://nodejs.org/) (v18+)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

### Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/kerfaiyass54/world-cup-app.git
   cd world-cup-app
   ```

2. **Set up the environment:**
   ```bash
   # Start the Docker services (PostgreSQL, Kafka, etc.)
   cd wc-app-environment
   docker-compose up -d
   ```

3. **Build and run the backend:**
   ```bash
   cd wc-app-back/world-cup-app
   mvn clean install
   mvn spring-boot:run
   ```

4. **Set up the analytics service:**
   ```bash
   cd ../wc-app-ai/worldcup_analytics
   pip install -r requirements.txt
   python -m kafka_client.producer
   ```

5. **Build and run the frontend:**
   ```bash
   cd ../wc-app-front
   npm install
   ng serve
   ```

6. **Access the application:**
   - Backend API: `http://localhost:8080`
   - Frontend: `http://localhost:4200`
   - Kafka UI: `http://localhost:8085`

---



## 🔧 Configuration

### Environment Variables

Create a `.env` file in the root directory for environment-specific configurations:

```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5437
DB_NAME=worldcup
DB_USER=alibou
DB_PASSWORD=alibou

# Kafka Configuration
KAFKA_BROKER=wc_kafka:29093

# Application Ports
BACKEND_PORT=8080
FRONTEND_PORT=4200
```

### Database Initialization

The project includes SQL scripts for initializing the database schema:

```sql
-- wc-app-db/init/wc-analytics.sql
CREATE TABLE champion_stats (
    id SERIAL PRIMARY KEY,
    country TEXT UNIQUE NOT NULL,
    wins INTEGER NOT NULL
);
-- ... other tables
```

### Customizing the Frontend

The Angular frontend uses a modern design with Bootstrap 5. Customize the styles in `src/styles.css` and components in `src/app/`.

---

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

### Development Setup

1. Fork the repository and clone your fork:
   ```bash
   git clone https://github.com/kerfaiyass54/world-cup-app.git
   ```

2. Set up the environment as described in the [Installation](#%E2%9C%93-installation) section.

3. Create a new branch for your feature or bugfix:
   ```bash
   git checkout -b feature/your-feature-name
   ```

### Code Style Guidelines

- **Java:** Follow the Spring Boot coding conventions and use Lombok for reducing boilerplate code.
- **Python:** Follow PEP 8 guidelines and use type hints where applicable.
- **Angular:** Follow Angular Style Guide and use TypeScript best practices.
- **General:** Write clear, concise, and well-documented code.

### Pull Request Process

1. Ensure your code passes all tests.
2. Write a clear description of your changes in the pull request.
3. Reference any issues that your pull request resolves.
4. Request a review from a maintainer.


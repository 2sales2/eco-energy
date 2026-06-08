# ⚡ EcoEnergy — Energy Consumption Control System

A hands-on Java project implementing a real-world business rule for an energy company that needs **data persistence and consistency** using Java, Hibernate ORM, and PostgreSQL.

---

## 🚀 Technologies

| Technology | Version |
|---|---|
| Java | 21 |
| PostgreSQL | 18 |
| Hibernate ORM | 6.6.0.Final |
| Jakarta Persistence API | 3.1.0 |
| Maven | 3.9+ |
| Docker | 29.5+ |

---

## 🐳 Running with Docker (Recommended)

No need to install PostgreSQL or configure anything manually. Docker handles everything.

### Prerequisites
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/your-username/ecoenergy.git
cd ecoenergy

# 2. Run everything with a single command
docker compose up --build
```

Docker will automatically:
- Start a PostgreSQL 18 container with the database and user pre-configured
- Build the Java application inside a container
- Connect both containers and run the application

### Expected output
```
ecoenergy-app | Sector: Manufacturing Floor | Consumption: 8200.5 | Level: HIGH
ecoenergy-app | Sector: Headquarters | Consumption: 3100.0 | Level: HIGH
ecoenergy-app | Average: 2500.0
ecoenergy-app | Sector: Data Center | Tariff: 0.85
ecoenergy-app | Sector: Manufacturing Floor | Tariff: 0.90
ecoenergy-app | Sector: Human Resources | Tariff: 0.99
ecoenergy-app exited with code 0
```

---

## 🔧 Running Locally (Manual Setup)

### Prerequisites
- [Java JDK 21+](https://openjdk.org/)
- [Apache Maven 3.9+](https://maven.apache.org/)
- [PostgreSQL 18+](https://www.postgresql.org/)

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/your-username/ecoenergy.git
cd ecoenergy
```

**2. Create the database and user in PostgreSQL**
```sql
CREATE USER ecoenergy WITH PASSWORD 'ecoenergy123';
CREATE DATABASE ecoenergy_db OWNER ecoenergy;
```

**3. Configure the persistence.xml**
```bash
cp src/main/resources/META-INF/persistence.xml.example \
   src/main/resources/META-INF/persistence.xml
```

Edit `persistence.xml` and fill in your credentials:
```xml
<property name="jakarta.persistence.jdbc.url"
          value="jdbc:postgresql://localhost:5432/ecoenergy_db"/>
<property name="jakarta.persistence.jdbc.user"      value="YOUR_USER"/>
<property name="jakarta.persistence.jdbc.password"  value="YOUR_PASSWORD"/>
```

**4. Configure PostgreSQL authentication (Fedora/RHEL only)**

In `/var/lib/pgsql/data/pg_hba.conf`, set the local connection to `md5`:
```
host    all    all    127.0.0.1/32    md5
```
Then restart PostgreSQL:
```bash
sudo systemctl restart postgresql
```

**5. Run the project**
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.ecoenergy.App"
```

---

## 🏗️ Project Structure

```
.
├── Dockerfile                        # Multi-stage build for the Java app
├── docker-compose.yml                # Orchestrates PostgreSQL + Java containers
├── pom.xml                           # Maven dependencies and build config
└── src
    └── main
        ├── java
        │   └── com
        │       └── ecoenergy
        │           ├── App.java                  # Entry point — orchestrates the flow
        │           ├── dao
        │           │   └── EnergyLogDAO.java     # Data Access Object (DAO pattern)
        │           ├── model
        │           │   ├── EnergyLog.java        # JPA Entity mapped to energy_logs table
        │           │   └── ConsumptionLevel.java # Enum: LOW | MODERATE | HIGH
        │           └── util
        │               └── JPAUtil.java          # EntityManagerFactory (Singleton pattern)
        └── resources
            └── META-INF
                ├── persistence.xml              # Local config (ignored by git)
                └── persistence.xml.example     # Template for manual setup
```

---

## 🎯 Business Rules

Energy consumption is categorized into three levels before saving each record:

| Level | Consumption |
|---|---|
| `LOW` | Below 1,000 kWh |
| `MODERATE` | Between 1,000 and 3,000 kWh |
| `HIGH` | Above 3,000 kWh |

---

## 🔍 Features

- **Save** energy consumption records with sector, date, consumption (kWh), tariff, and level
- **Update** an existing record's tariff directly in the database
- **Find HIGH consumption logs** — returns all records categorized as HIGH
- **Average consumption by sector** — calculates the average kWh for a given sector
- **Find logs above a tariff limit** — returns all records where the applied tariff exceeds a given value

---

## 🏛️ Design Patterns

### Singleton — `JPAUtil`
The `EntityManagerFactory` is expensive to create as it reads configuration and opens the database connection pool. The Singleton pattern ensures it is created **only once** and reused throughout the application lifecycle.

### DAO — `EnergyLogDAO`
The Data Access Object pattern centralizes all database operations in a dedicated class, keeping `App.java` focused on business logic. This follows the **Single Responsibility Principle**.

```
App.java
  └─calls─▶ EnergyLogDAO (DAO pattern)
               └─uses─▶ JPAUtil (Singleton pattern)
                           └─connects─▶ PostgreSQL
```

---

## 🐳 Docker Architecture

```
docker-compose.yml
  ├── ecoenergy-db   → PostgreSQL 18 container (pre-configured)
  └── ecoenergy-app  → Java app built via Dockerfile
                           ├── Stage 1: Maven builds the fat jar
                           └── Stage 2: JRE runs the jar
```

The application reads database credentials from environment variables injected by Docker Compose, falling back to `persistence.xml` values when running locally.

---

## 📄 License

This project was developed as a practical exercise. Feel free to use it as a reference.

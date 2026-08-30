\# 🍃 Food Waste Reduction Platform



A full-stack web application designed to bridge the gap between food donors (restaurants, caterers) and NGOs to reduce food wastage and help those in need.



\## 🚀 Features

\- \*\*Role-Based Dashboards:\*\* Distinct user experiences for Donors (Restaurants) and NGOs.

\- \*\*Donation Management:\*\* Restaurants can easily post available surplus food, including quantity, type, and expiration time.

\- \*\*Live Claiming System:\*\* NGOs can browse a feed of available donations in their area and instantly claim them for pickup.

\- \*\*Status Tracking:\*\* Track the lifecycle of food donations from "Available" to "Claimed" and "Handed-Off".



\## 🛠️ Tech Stack

\- \*\*Frontend:\*\* React.js, Vite, Tailwind CSS

\- \*\*Backend:\*\* Java 21, Spring Boot, RESTful APIs

\- \*\*Database:\*\* MySQL

\- \*\*Tools:\*\* VS Code, Maven, npm, Git



\## ⚙️ Local Setup Instructions



\### Prerequisites

\- Java 21+

\- Node.js \& npm

\- MySQL Server



\### 1. Database Setup

1\. Create a MySQL database named `food\_waste\_reduction`.

2\. Update the `application.properties` file in the `backend/src/main/resources` folder with your MySQL username and password.



\### 2. Backend Setup (Spring Boot)

1\. Open the `backend` folder in your IDE.

2\. Run the `FoodWasteApplication.java` file to start the Tomcat server.



\### 3. Frontend Setup (React/Vite)

1\. Open a terminal and navigate to the `frontend` folder.

2\. Install the required dependencies:

&#x20;  ```bash

&#x20;  npm install


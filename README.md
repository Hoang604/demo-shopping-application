## Phenikaa University - Object-Oriented Programming Project: Shopping Application Demo

**Author:** Dinh Viet Hoang - 23010051

This repository contains a demo shopping application developed as an Object-Oriented Programming (OOP) project at Phenikaa University. The application demonstrates core functionalities of an e-commerce platform, including user management, product catalog, shopping cart, and order processing, along with secure user authentication.

**Project Features:**

*   **User Management:**
    *   Add new users
    *   Edit existing user information
    *   Remove users
*   **Product Management:**
    *   Add new products (admin only)
    *   Edit existing product information (e.g., name, price, description) (admin only)
    *   Remove products (admin only)
*   **Shopping Cart:**
    *   Add products to the cart
    *   Remove products from the cart
*   **Order Management:**
    *   Place orders
    *   View order history
*   **Authentication:**
    *   Secure user login and registration.
    *   Check authentication for each endpoint

**Technical Stack:**

*   Technologies and frameworks
    *   **Backend:** Java with Spring Boot.
    *   **Database:** MySQL.
    *   **Frontend:** Spring Boot thymeleaf.

**Prerequisites:**

*   **Docker:** Ensure Docker is installed on your system. You can download it from the official Docker website: [https://www.docker.com/get-started](https://www.docker.com/get-started)
*   **Docker Compose:** Ensure Docker Compose is installed. It usually comes bundled with Docker Desktop. You can find more information here: [https://docs.docker.com/compose/](https://docs.docker.com/compose/)

**Running the Application:**

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/Hoang604/demo-shopping-application.git
    ```
2.  **Build and Run with Docker Compose:**
    Execute the following command in the directory containing the `docker-compose.yml` file:
    ```bash
    docker-compose up --build -d
    ```
    This command will build the necessary Docker images (if they don't exist) and start the application containers in detached mode.

**Accessing the Application:**

Once the containers are running, you can access the application through your web browser.

*   **URL:** `localhost:8080`

**Stopping the Application:**

To stop the application, run the following command in the same directory:

```bash
docker-compose down

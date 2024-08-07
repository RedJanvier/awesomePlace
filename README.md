<div align="center"
  
  <div>
    <img src="https://img.shields.io/badge/-TypeScript-black?style=for-the-badge&logoColor=white&logo=typescript&color=3178C6" alt="typescript" />
  </div>

  <h3 align="center">Awesome Marketplace API</h3>
</div>

## 📋 <a name="table">Table of Contents</a>

1. 🤖 [Introduction](#introduction)
2. ⚙️ [Tech Stack](#tech-stack)
3. 🔋 [Features](#features)
4. 🚀 [Quick Start](#quick-start)

## <a name="introduction">🤖 Introduction</a>

Built with Nest.js & SpringBoot, Awesome Marketplace API is a RESTful API for an online marketplace that allows users to buy and sell products, manage their inventory and process orders.

## <a name="tech-stack">⚙️ Tech Stack</a>

- Nest.js
- TypeScript
- Java
- SpringBoot
- Kafka
- SendGrid
- PostgreSQL

## <a name="features">🔋 Features</a>

👉 **Authentication**: A dedicated service to manage authentication and authorization composed by user registration with email verification, login, refresh token and logout

👉 **Products Service**: Another separate service which is managing the products and their quantities in order to keep the inventory updated

👉 **Management of orders**: Receive, list and manage all orders is managed on a separate SpringBoot service that handles the order until it's completed

👉 **Notifications**: By the help of an external service (SendGrid) we send email verification link, order status update and more through a separate NestJS service.

👉 **Orders History**: View your history of orders which are ordered from recent to oldest.

👉 **Async Communication**: Different services are communicating asynchronously through Kafka as a broker which significantly improves the order processing and user management on register.

and many more, including code architecture and reusability. 

## <a name="quick-start">🚀 Quick Start</a>

Follow these steps to set up the project locally on your machine.

**Prerequisites**

Make sure you have the following installed on your machine:

- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/products/docker-desktop/) (Should be enough standalone)

**Cloning the Repository**

```bash
git clone https://github.com/redjanvier/awesome-marketplace-api.git
cd awesome-marketplace-api
```


**Set Up Environment Variables**

Create a new file named `.env` in the auth and notification services following the `.env.example` in the respective service.

Feel free to customize also the variable of `JWT` to match also in gateway which is handling authorization.

**Build and Run the Project**

```bash
docker-compose up --build
```

Open [http://localhost:8080](http://localhost:8080) in your browser to view the project.

# Banking Application

A full-stack banking application built with Spring Boot and React. This application demonstrates a modern, microservices-based architecture using Kafka for event streaming, Oracle for data persistence, and a React frontend with TypeScript.

## Features

- Account balance viewing and management
- Transaction history
- Scheduled payments
- Real-time transaction processing with Kafka
- Responsive React frontend
- RESTful API backend

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.0
- Oracle Database
- Apache Kafka
- JPA/Hibernate
- Maven

### Frontend
- React with TypeScript
- Vite
- Chakra UI
- React Query
- React Router
- Axios

## Project Structure

## Prerequisites

- Java 17
- Node.js and npm
- Docker and Docker Compose
- Oracle account for database image

## Setup and Installation

1. Clone the repository:
```bash
git clone https://github.com/juhoson/banking_app.git
cd banking_app
```
2. Start the infrastructure services:
```
docker compose up -d
```
3. Build and run the backend:
```
cd app
mvn clean install
mvn spring-boot:run
```
4. Install frontend dependencies and start the development server:
```
cd client
npm install
npm run dev
```

# API Documentation

## Base URL
http://localhost:8080/api

## Account Endpoints

### Get Account Balance
Returns the current balance and status of an account.

**Endpoint:** `GET /accounts/{accountId}/balance`

**Response:**
```json
{
    "accountId": "TEST123",
    "amount": 1000.00,
    "status": "ACTIVE"
}
```
### Make a Deposit
Deposits money into an account.

**Endpoint:** `POST /accounts/{accountId}/deposit`

**Query Parameters:** 
- amount: Decimal number representing the deposit amount

**Example:** `POST /accounts/TEST123/deposit?amount=100.00`
**Response:** HTTP 200 OK

### Make a Withdrawal
Withdraws money from an account.

**Endpoint:** `POST /accounts/{accountId}/withdraw`

**Query Parameters:**
- amount: Decimal number representing the withdrawal amount

**Example:** `POST /accounts/TEST123/withdraw?amount=50.00`
**Response:** HTTP 200 OK

## Transaction Endpoints
### Get Transaction History
Returns a list of transactions for an account.

**Endpoint:** `GET /accounts/{accountId}/transactions`

**Response:**
```
[
    {
        "id": "111283b4-9eaf-45e6-b661-7ae3dba27c80",
        "accountId": "TEST123",
        "amount": 100.00,
        "type": "DEPOSIT",
        "timestamp": "2024-01-06T14:30:00",
        "description": "Deposit"
    },
    {
        "id": "222283b4-9eaf-45e6-b661-7ae3dba27c81",
        "accountId": "TEST123",
        "amount": -50.00,
        "type": "WITHDRAWAL",
        "timestamp": "2024-01-06T15:30:00",
        "description": "Withdrawal"
    }
]
```

### Get Transaction Details
Returns details of a specific transaction.

**Endpoint:** `GET /accounts/{accountId}/transactions/{transactionId}`
**Response:**

```
{
    "id": "111283b4-9eaf-45e6-b661-7ae3dba27c80",
    "accountId": "TEST123",
    "amount": 100.00,
    "type": "DEPOSIT",
    "timestamp": "2024-01-06T14:30:00",
    "description": "Deposit"
}
```

## Payment Endpoints

### Schedule a Payment

Creates a new scheduled payment.

**Endpoint:** `POST /payments/{accountId}/schedule`

**Request Body:**
```
{
    "amount": 50.00,
    "scheduledDate": "2024-01-20T10:00:00",
    "description": "Scheduled payment"
}
```
**Response:**
``` 
{
    "id": 1,
    "accountId": "TEST123",
    "amount": 50.00,
    "scheduledDate": "2024-01-20T10:00:00",
    "description": "Scheduled payment",
    "status": "PENDING"
}
```
## Get Scheduled Payments
Returns a list of scheduled payments for an account.

**Endpoint:** `GET /payments/{accountId}/scheduled`
**Response:**
``` 
[
    {
        "id": 1,
        "accountId": "TEST123",
        "amount": 50.00,
        "scheduledDate": "2024-01-20T10:00:00",
        "description": "Scheduled payment",
        "status": "PENDING"
    }
]
```

### Error Responses
## General Error Response Format
```
{
    "message": "Error description"
}
```

## Common HTTP Status Codes
- 200: Success
- 400: Bad Request (invalid input)
- 404: Not Found (invalid accountId or transactionId)
- 500: Internal Server Error

## Event Streaming

The application uses Kafka for event streaming. Each transaction generates an event in the banking.transactions topic.

## Transaction Event Format
```
{
    "id": "111283b4-9eaf-45e6-b661-7ae3dba27c80",
    "accountId": "TEST123",
    "amount": 100.00,
    "type": "DEPOSIT",
    "timestamp": "2024-01-06T14:30:00",
    "description": "Deposit"
}
```
## Rate Limiting
- No rate limiting implemented in the current version

## Authentication
- No authentication required in the current version
- Planning to add JWT-based authentication in future versions
# Crypto Trading System

## Overview

This project is a simple crypto trading system built using the Spring Boot framework and an in-memory H2 database. The system supports the following functionalities:

1. Users can buy/sell supported crypto trading pairs.
2. Users can view the list of their trading transactions.
3. Users can view their cryptocurrency wallet balance.

The system currently supports two trading pairs: Ethereum (ETHUSDT) and Bitcoin (BTCUSDT).

## Features

1. **Price Aggregation**: Fetches price data from Binance and Huobi every 10 seconds and stores the best prices in the database.
2. **Trade Execution**: Allows users to execute trades based on the latest aggregated prices.
3. **Balance Inquiry**: Users can check their wallet balances.
4. **Trade History**: Users can retrieve their trading history.

## Assumptions

1. Users are already authenticated and authorized to access the APIs.
2. Each user has an initial wallet balance of 50,000 USDT.
3. The system only supports ETHUSDT and BTCUSDT trading pairs.

## Getting Started

### Prerequisites

- Java 17
- Maven 3.9.0 or higher

### Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/AntonyLe23/simple-crypto-trade-service.git
    ```

2. **Build the project**:
    ```sh
    mvn clean install
    ```

3. **Run the application**:
    ```sh
    mvn spring-boot:run
    ```

### Api endpoints
- Swagger UI: http://localhost:8080/swagger-ui/index.html

### Contact
For any questions or support, please reach out to phat.lethinh@outlook.com.vn

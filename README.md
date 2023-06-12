# Car Renting APIs

This is a multi-module project consisting of microservices for managing contracts, customers, and vehicles.

## Prerequisites

Before running the project, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 11 or higher
- Apache Maven
- MongoDB
- Apache Kafka

### MongoDB Installation

1. Download MongoDB Community Edition from the official website: https://www.mongodb.com/try/download/community
2. Follow the installation instructions specific to your operating system: https://docs.mongodb.com/manual/installation/
3. Start MongoDB server by running the appropriate command for your OS.

### Apache Kafka Installation

1. Download Apache Kafka from the official website: https://kafka.apache.org/downloads
2. Extract the downloaded archive to a preferred location.
3. Navigate to the Kafka installation directory.
4. Start the ZooKeeper server using the following command: ``` bin/zookeeper-server-start.sh config/zookeeper.properties ```
5. Start the Kafka server using the following command: ```bin/kafka-server-start.sh config/server.properties```


## Build and Run

Follow these steps to build and run the project:

1. Clone the repository.
2. Navigate to the project root directory.
3. Build the project using Maven: ```mvn clean install```
4. Start each microservice individually by navigating to its respective directory and running the following command: ```mvn spring-boot:run```

- Start the `contract-service` microservice.
- Start the `customer-service` microservice.
- Start the `vehicle-service` microservice.
- Start the `database-service` microservice.

## Usage

Once the microservices are up and running, you can access the following endpoints:

- Contract Service: http://localhost:8080/api/contracts
- Customer Service: http://localhost:8081/api/customers
- Vehicle Service: http://localhost:8082/api/vehicles

Please refer to the Swagger documentation for more details on the available endpoints and their usage.

## Additional Configuration

The project uses default configuration values for connecting to MongoDB and Kafka. If you have custom configurations, you can modify the respective configuration files:

- MongoDB configuration: `micros/<microservice>/src/main/resources/application.properties`
- Kafka configuration: `micros/DatabaseService/src/main/resources/application.properties`

Make sure to update the configuration values according to your environment setup.

## License

This project is licensed under the [MIT License](https://github.com/Mohanad-ALNUNU/CarRenting-Mircoservices-back/blob/master/LICENSE).


## Contributing

Contributions are welcome! If you have any improvements or bug fixes, please submit a pull request.

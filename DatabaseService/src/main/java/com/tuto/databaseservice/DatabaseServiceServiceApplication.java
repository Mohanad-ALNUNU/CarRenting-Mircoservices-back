package com.tuto.databaseservice;


import com.tuto.contract.entity.Contract;
import com.tuto.customer.entity.Customer;
import com.tuto.vehicle.entity.Vehicle;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableKafka
public class DatabaseServiceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseServiceServiceApplication.class, args);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Contract> contractKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Contract> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(contractConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Customer> customerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Customer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(customerConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Vehicle> vehicleKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Vehicle> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(vehicleConsumerFactory());
        return factory;
    }

    private DefaultKafkaConsumerFactory<String, Contract> contractConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer<>(Contract.class));
    }

    private DefaultKafkaConsumerFactory<String, Customer> customerConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer<>(Customer.class));
    }

    private DefaultKafkaConsumerFactory<String, Vehicle> vehicleConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer<>(Vehicle.class));
    }

    private Map<String, Object> consumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, "inserting");
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configurations.put(JsonDeserializer.TRUSTED_PACKAGES, "com.tuto.databaseservice.entity");
        return configurations;
    }
}

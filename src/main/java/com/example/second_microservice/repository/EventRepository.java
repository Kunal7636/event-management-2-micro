package com.example.second_microservice.repository;

import com.example.second_microservice.eventservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
    // You can define custom query methods here if needed
}
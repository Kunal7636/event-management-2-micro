package com.example.second_microservice.service;

import com.example.second_microservice.exception.ResourceNotFoundException;
import com.example.second_microservice.repository.EventRepository;
import com.example.second_microservice.eventservice.model.Event;
import com.example.second_microservice.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public Event saveEvent(Event event) {
        // The save() method is automatically provided by MongoRepository
        return eventRepository.save(event);
    }

    public Event getEventById(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        // Fetch organizer details
        try {
            User user = restTemplate.getForObject(userServiceUrl + "/" + event.getOrganizerId(), User.class);
            // Handle user details as needed
            if (user != null) {
//                event.setOrganizerName(user.getName());
//                event.setOrganizerEmail(user.getEmail());
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Unable to fetch organizer details");
        }

        return event;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}

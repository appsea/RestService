package com.exuberant.bookmyflight.service;

import com.exuberant.bookmyflight.model.Flight;
import com.exuberant.bookmyflight.model.Location;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rakesh on 23-Oct-2017.
 */
@Service
public class CacheFlightDataService implements FlightDataService{

    private Collection<Flight> flights = new ArrayList<>();

    public Collection<Flight> getFlights(){
        if(flights.size()==0){
            flights.add(new Flight(1, "Air India",LocalDate.now(), "10 AM", "BOM", "GLA", "/app/assets/images/angularconnect-shield.png", "/app/assets/images/angularconnect-shield.png", new Location("Powai", "Mumbai", "India")));
            flights.add(new Flight(2, "Lufthansa Airline",LocalDate.now(), "11 AM", "LON", "NYK", "/app/assets/images/angularconnect-shield.png", "/app/assets/images/basic-shield.png", new Location("Buchanan", "Glasgow", "UK")));
            flights.add(new Flight(3, "British Airline",LocalDate.now(), "12 PM", "LGW", "DEL", "/app/assets/images/ng-conf.png", "/app/assets/images/ng-conf.png", new Location("Twin Tower", "Chicago", "USA")));
            flights.add(new Flight(4, "Virgin Airlines",LocalDate.now(), "8 PM", "EDN", "FRA", "/app/assets/images/ng-nl.png", "/app/assets/images/ng-nl.png", new Location("Nature Park", "Berlin", "Germany")));
        }
        return flights;
    }
}
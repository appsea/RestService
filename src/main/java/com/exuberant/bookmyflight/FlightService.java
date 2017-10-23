package com.exuberant.bookmyflight;

import com.exuberant.bookmyflight.model.Flight;
import com.exuberant.bookmyflight.service.FlightDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class FlightService {

    @Autowired
    private FlightDataService flightDataService;

    public Collection<Flight> getFlights() {
        return flightDataService.getFlights();
    }
}

package com.exuberant.rest.bookmyflight;

import com.exuberant.rest.bookmyflight.model.Flight;
import com.exuberant.rest.bookmyflight.service.FlightDataService;
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

    public Flight saveFlight(Flight flight) {
        return flightDataService.saveFlight(flight);
    }
}

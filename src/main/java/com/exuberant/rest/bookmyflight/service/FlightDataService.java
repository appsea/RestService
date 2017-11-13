package com.exuberant.rest.bookmyflight.service;

import com.exuberant.rest.bookmyflight.model.Flight;

import java.util.Collection;

/**
 * Created by rakesh on 23-Oct-2017.
 */
public interface FlightDataService {
    Collection<Flight> getFlights();

    Flight saveFlight(Flight flight);
}

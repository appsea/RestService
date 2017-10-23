package com.exuberant.bookmyflight.service;

import com.exuberant.bookmyflight.model.Flight;

import java.util.Collection;

/**
 * Created by rakesh on 23-Oct-2017.
 */
public interface FlightDataService {
    Collection<Flight> getFlights();
}

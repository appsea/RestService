package com.exuberant.rest.survey.controller;

import com.exuberant.rest.bookmyflight.FlightService;
import com.exuberant.rest.bookmyflight.model.Flight;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/bookmyflights")
public class BookMyFlightController {

    public static final Log log = LogFactory.getLog(BookMyFlightController.class);
    
    @Autowired
    private FlightService flightService;

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping(path = "/flights", produces = "application/json; charset=UTF-8")
    public Collection<Flight> flights() throws Exception {
        log.info("Getting flights....");
        return flightService.getFlights();
    }

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping(path = "/flights/{id}", produces = "application/json; charset=UTF-8")
    public Flight flights(@PathVariable int id) throws Exception {
        log.info("Getting flight for id: " + id);
        return flightService.getFlights().stream().filter(flight->flight.getId()== id).findFirst().orElse(null);
    }

    @CrossOrigin(origins = "http://localhost:8808")
    @RequestMapping(path = "/flights", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    public Flight save(@RequestBody Flight flight) throws Exception {
        log.info("Saving New flight");
        return flightService.saveFlight(flight);
    }
}
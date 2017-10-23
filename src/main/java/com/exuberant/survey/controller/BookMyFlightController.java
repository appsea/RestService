package com.exuberant.survey.controller;

import com.exuberant.bookmyflight.FlightService;
import com.exuberant.bookmyflight.model.Flight;
import com.exuberant.survey.model.Question;
import com.exuberant.survey.service.PaperSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/bookmyflights")
public class BookMyFlightController {

    @Autowired
    private FlightService flightService;

    @RequestMapping("/flights")
    public Collection<Flight> flights() throws Exception {
        return flightService.getFlights();
    }
}
package com.cinebook.movieservice.controller;

import com.cinebook.movieservice.entity.Cinema;
import com.cinebook.movieservice.entity.City;
import com.cinebook.movieservice.repository.CinemaRepository;
import com.cinebook.movieservice.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private CityRepository cityRepo;

    @Autowired
    private CinemaRepository cinemaRepo;

    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityRepo.findAll();
    }

    @GetMapping("/cities/{cityId}/cinemas")
    public List<Cinema> getCinemasByCity(@PathVariable Long cityId) {
        return cinemaRepo.findByCityId(cityId);
    }
}
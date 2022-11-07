package com.fleet.demo.service;

import com.fleet.demo.entity.City;

import java.util.List;
import java.util.Optional;


public interface SearchService {

    List<City> citySearch(Optional<String> input);
}

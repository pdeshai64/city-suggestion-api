package com.fleet.demo.controller;

import com.fleet.demo.dto.CityDto;
import com.fleet.demo.entity.City;
import com.fleet.demo.service.SearchService;
import com.fleet.demo.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    @Qualifier("cityEntityToDtoConverter")
    private Converter<City, CityDto> cityEntityDtoConverter;


    @Autowired
    @Qualifier("suggestionValidator")
    private Validator validator;

    @GetMapping(value = "suggest", name = "suggest", produces = "application/json")
    @ResponseBody
    public List<CityDto> getCitySuggestion(@RequestParam(name = "q", required = false) Optional<String> query) {

        validator.validate(query);
        log.info("Validation success.");
        return searchService.citySearch(query)
                .stream().map(city -> cityEntityDtoConverter.convert(city))
                .collect(Collectors.toList());
    }

}

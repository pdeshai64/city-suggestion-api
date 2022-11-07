package com.fleet.demo.converter;

import com.fleet.demo.dto.CityDto;
import com.fleet.demo.entity.City;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CityEntityToDtoConverter implements Converter<City, CityDto> {
    @Override
    public CityDto convert(City source) {
        return CityDto.builder()
                .fips(source.getFips()).name(source.getName()).state(source.getState()).build();
    }
}

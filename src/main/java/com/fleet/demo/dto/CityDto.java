package com.fleet.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDto {

    private String fips;

    private String state;

    private String name;

}

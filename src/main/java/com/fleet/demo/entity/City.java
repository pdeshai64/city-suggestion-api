package com.fleet.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "city", indexes = {
        @Index(name = "cityIndex", columnList = "name"),
        @Index(name = "stateIndex", columnList = "state"),
        @Index(name = "cityStateIndex", columnList = "name , state")
})
@Data
public class City implements Serializable {

    @Id
    private String fips;

    private String state;

    private String name;

}

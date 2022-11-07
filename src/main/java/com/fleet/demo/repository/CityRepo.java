package com.fleet.demo.repository;

import com.fleet.demo.entity.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<City, String> {

    List<City> findByNameIgnoreCaseStartsWith(String name, Pageable pageable);

    List<City> findByStateIgnoreCaseStartsWith(String state, Pageable pageable);

    List<City> findByNameIgnoreCaseStartsWithOrStateIgnoreCaseStartsWith(String name, String state,
                                                                         Pageable pageable);

    List<City> findByNameIgnoreCaseStartsWithAndStateIgnoreCaseStartsWith(String name, String state,
                                                                          Pageable pageable);

}

package com.fleet.demo.repository;

import com.fleet.demo.entity.BulkInsertCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkCityRepo extends JpaRepository<BulkInsertCity, String> {

}

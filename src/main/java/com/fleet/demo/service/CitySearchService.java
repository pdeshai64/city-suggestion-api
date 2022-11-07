package com.fleet.demo.service;

import com.fleet.demo.entity.City;
import com.fleet.demo.repository.CityRepo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Slf4j
@Setter
public class CitySearchService implements SearchService {

    @Autowired
    private CityRepo cityRepo;

    @Value("${search.limit:5}")
    private int searchLimit;

    @Override
    public List<City> citySearch(Optional<String> query) {
        /*
        We need to return 5 object. We have three case
        1. Query is blank : We will return top five in this case
        2. Query have only 2 char: We will search in state order by fips and select top 5
        2.1 Query have 1 char: We will search in both City and state order by fips and select top 5
        3. More than 2 char: Same as 2 but only search in city
        4. Query have coma separated value:
            First match with city and second match with state order by flip and select top 5

         */
        Pageable pageable = PageRequest.of(0, searchLimit, Sort.Direction.ASC, "fips");
        if (query.isPresent()) {
            log.info("Query is present");
            String[] input = query.get().split(",");
            String firstVal = input[0].trim();
            if (input.length == 2) {
                //case 4
                log.info("Query with city and state");
                String state = input[1].trim();
                return cityRepo.findByNameIgnoreCaseStartsWithAndStateIgnoreCaseStartsWith(
                        firstVal, state, pageable);
            } else {
                if (firstVal.length() == 2) {
                    //case 2
                    log.info("Query with state only");
                    List<City> stateList = cityRepo.findByStateIgnoreCaseStartsWith(firstVal, pageable);
                    if (stateList.size() < searchLimit) {
                        log.info("adding additional city data");
                        List<City> cityList = cityRepo
                                .findByNameIgnoreCaseStartsWith(firstVal, pageable);
                        for (int i = 0; i <= (searchLimit - stateList.size()); i++) {
                            stateList.add(cityList.get(i));
                        }
                    }
                    return stateList;
                } else if (firstVal.length() == 1) {
                    //case2.1
                    log.info("Query with abstract data");
                    return cityRepo.findByNameIgnoreCaseStartsWithOrStateIgnoreCaseStartsWith(
                            firstVal, firstVal, pageable);
                } else {
                    //case 3
                    log.info("Query with city only");
                    return cityRepo.findByNameIgnoreCaseStartsWith(firstVal, pageable);
                }
            }
        } else {
            //case 1
            log.info("Query not present, default query.");
            return cityRepo.findAll(pageable).getContent();
        }
    }
}

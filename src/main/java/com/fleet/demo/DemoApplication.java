package com.fleet.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.demo.entity.BulkInsertCity;
import com.fleet.demo.repository.BulkCityRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class DemoApplication {

    @Autowired
    BulkCityRepo bulkInsertCityRepo;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData() {
        return args -> {
            if (Objects.nonNull(args) && args.length > 0) {
                Path path = Paths.get(args[0]);
                log.debug("File path {}", args[0]);
                log.info("Reading File");
                BufferedReader br = Files.newBufferedReader(path);
                List<BulkInsertCity> input = new ObjectMapper().readValue(br, new TypeReference<List<BulkInsertCity>>() {
                });
                log.info("json converted to list");
                bulkInsertCityRepo.saveAll(input);
                log.info("Bulk insert completed");
            } else {
                log.warn("File url not found.");
            }
        };

    }
}

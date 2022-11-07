package com.fleet.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.demo.dto.CityDto;
import com.fleet.demo.entity.BulkInsertCity;
import com.fleet.demo.repository.CityRepo;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoApplicationTests {

    private static boolean setUpToDo = true;
    @Autowired
    CityRepo cityRepo;
    TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;


    @BeforeAll
    public void setup() throws IOException {
        if (setUpToDo) {
            InputStreamReader stream = new InputStreamReader(new ClassPathResource("data.json").getInputStream());
            List<BulkInsertCity> input = new ObjectMapper().readValue(stream, new TypeReference<List<BulkInsertCity>>() {
            });
            cityRepo.saveAll(input);
            setUpToDo = false;
        }
    }

    @Test
    public void testSuggestWithCityAndState() throws JSONException {
        List<CityDto> cityDtos = restTemplate.getForObject(createURLWithPort(""), List.class);
        assertTrue(cityDtos.size() == 5);
        String expacted = "[\n" +
                "    {\n" +
                "        \"fips\": \"01001\",\n" +
                "        \"state\": \"AL\",\n" +
                "        \"name\": \"ABtauga\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01003\",\n" +
                "        \"state\": \"AL\",\n" +
                "        \"name\": \"ABldwin\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01005\",\n" +
                "        \"state\": \"AL\",\n" +
                "        \"name\": \"Barbour\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01007\",\n" +
                "        \"state\": \"AL\",\n" +
                "        \"name\": \"ABibb\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01009\",\n" +
                "        \"state\": \"AB\",\n" +
                "        \"name\": \"Barunt\"\n" +
                "    }\n" +
                "]";
        JSONAssert.assertEquals(expacted, new JSONArray(cityDtos), false);
    }

    @Test
    public void testSuggestWithStateAndCity() throws JSONException {
        List<CityDto> cityDtos = restTemplate.getForObject(createURLWithPort("AB"), List.class);
        assertTrue(cityDtos.size() == 5);

        String expacted = "[\n" +
                "    {\n" +
                "        \"fips\": \"01009\",\n" +
                "        \"state\": \"AB\",\n" +
                "        \"name\": \"Barunt\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01011\",\n" +
                "        \"state\": \"AB\",\n" +
                "        \"name\": \"Bullock\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01013\",\n" +
                "        \"state\": \"AB\",\n" +
                "        \"name\": \"Butler\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01001\",\n" +
                "        \"state\": \"AL\",\n" +
                "        \"name\": \"ABtauga\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01003\",\n" +
                "        \"state\": \"AL\",\n" +
                "        \"name\": \"ABldwin\"\n" +
                "    }\n" +
                "]";

        JSONAssert.assertEquals(expacted, new JSONArray(cityDtos), false);
    }

    @Test
    public void testSuggestWithCity() throws JSONException {
        List<CityDto> cityDtos = restTemplate.getForObject(createURLWithPort("Bar"), List.class);
        assertTrue(cityDtos.size() == 4);

        String expacted = "[\n" +
                "    {\n" +
                "        \"fips\": \"01005\",\n" +
                "        \"state\": \"AL\",\n" +
                "        \"name\": \"Barbour\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01009\",\n" +
                "        \"state\": \"AB\",\n" +
                "        \"name\": \"Barunt\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01015\",\n" +
                "        \"state\": \"CL\",\n" +
                "        \"name\": \"Barhoun\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01021\",\n" +
                "        \"state\": \"DL\",\n" +
                "        \"name\": \"Barlton\"\n" +
                "    }\n" +
                "]";

        JSONAssert.assertEquals(expacted, new JSONArray(cityDtos), false);
    }

    @Test
    public void testSuggestWithCityAndStateMatch() throws JSONException {
        List<CityDto> cityDtos = restTemplate.getForObject(createURLWithPort("Bar, al"), List.class);
        assertTrue(cityDtos.size() == 1);

        String expacted = "[\n" +
                "    {\n" +
                "        \"fips\": \"01005\",\n" +
                "        \"state\": \"AL\",\n" +
                "        \"name\": \"Barbour\"\n" +
                "    }\n" +
                "]";

        JSONAssert.assertEquals(expacted, new JSONArray(cityDtos), false);
    }

    @Test
    public void testSuggestFail() throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("bar?"), String.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    public void testSuggestWithOnechar() throws JSONException {
        List<CityDto> cityDtos = restTemplate.getForObject(createURLWithPort("c"), List.class);
        assertTrue(cityDtos.size() == 5);

        String expacted = "[\n" +
                "    {\n" +
                "        \"fips\": \"01015\",\n" +
                "        \"state\": \"CL\",\n" +
                "        \"name\": \"Barhoun\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01017\",\n" +
                "        \"state\": \"CL\",\n" +
                "        \"name\": \"Chambers\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01019\",\n" +
                "        \"state\": \"DL\",\n" +
                "        \"name\": \"Cherokee\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01023\",\n" +
                "        \"state\": \"GL\",\n" +
                "        \"name\": \"Choctaw\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"fips\": \"01025\",\n" +
                "        \"state\": \"HL\",\n" +
                "        \"name\": \"Clarke\"\n" +
                "    }\n" +
                "]";

        JSONAssert.assertEquals(expacted, new JSONArray(cityDtos), false);
    }

    private String createURLWithPort(String query) {
        return "http://localhost:" + port + "/suggest?q=" + query;
    }
}

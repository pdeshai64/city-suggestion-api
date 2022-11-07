package com.fleet.demo.service;

import com.fleet.demo.repository.CityRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class CitySearchServiceTest {

    @Mock
    CityRepo cityRepo;

    @InjectMocks
    CitySearchService searchService;


    @Test
    public void testWithBlank() {
        //Arrange
        Page listAll = Mockito.mock(Page.class);
        Mockito.when(cityRepo.findAll(any(Pageable.class))).thenReturn(listAll);
        List list = Mockito.mock(List.class);
        Mockito.when(listAll.getContent()).thenReturn(list);
        searchService.setSearchLimit(5);
        //Act
        List result = searchService.citySearch(Optional.empty());

        //Assert
        assertEquals(list, result);

    }

    @Test
    public void testWithOneChar() {
        //Arrange
        List list = Mockito.mock(List.class);
        Mockito.when(cityRepo.findByNameIgnoreCaseStartsWithOrStateIgnoreCaseStartsWith(any(), any(), any())).thenReturn(list);
        searchService.setSearchLimit(5);
        //Act
        List result = searchService.citySearch(Optional.of("q"));

        //Assert
        assertEquals(list, result);
    }

    @Test
    public void testWithTwoChar() {
        //Arrange
        List list = Mockito.mock(List.class);
        Mockito.when(cityRepo.findByStateIgnoreCaseStartsWith(any(), any())).thenReturn(list);
        Mockito.when(list.size()).thenReturn(5);
        searchService.setSearchLimit(5);
        //Act
        List result = searchService.citySearch(Optional.of("qw"));

        //Assert
        assertEquals(list, result);
    }

    @Test
    public void testWithThreeChar() {
        //Arrange
        List list = Mockito.mock(List.class);
        Mockito.when(cityRepo.findByNameIgnoreCaseStartsWith(any(), any())).thenReturn(list);

        searchService.setSearchLimit(5);
        //Act
        List result = searchService.citySearch(Optional.of("qqw"));

        //Assert
        assertEquals(list, result);
    }

    @Test
    public void testWithCityAndState() {
        //Arrange
        List list = Mockito.mock(List.class);
        Mockito.when(cityRepo.findByNameIgnoreCaseStartsWithAndStateIgnoreCaseStartsWith(any(), any(), any())).thenReturn(list);
        searchService.setSearchLimit(5);
        //Act
        List result = searchService.citySearch(Optional.of("qqw, qa"));

        //Assert
        assertEquals(list, result);
    }

    @Test
    public void testWithTwoCharWithCitySearch() {
        //Arrange
        List list = Mockito.mock(List.class);
        List city = Mockito.mock(List.class);
        Mockito.when(cityRepo.findByStateIgnoreCaseStartsWith(any(), any())).thenReturn(list);
        Mockito.when(list.size()).thenReturn(3);
        Mockito.when(cityRepo.findByNameIgnoreCaseStartsWith(any(), any())).thenReturn(city);
        Mockito.when(list.add(any())).thenReturn(true);
        searchService.setSearchLimit(5);
        //Act
        List result = searchService.citySearch(Optional.of("qw"));

        //Assert
        assertEquals(list, result);
    }


}

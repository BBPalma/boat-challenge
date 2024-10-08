package com.bernardo.boatchallenge.controllers;

import com.bernardo.boatchallenge.entities.Boat;
import com.bernardo.boatchallenge.services.BoatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoatControllerTest {

    @Mock
    private BoatService boatService;

    @InjectMocks
    private BoatController boatController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBoats_ReturnsListOfBoats() {
        Boat boat1 = new Boat();
        Boat boat2 = new Boat();
        when(boatService.getAllBoats()).thenReturn(Arrays.asList(boat1, boat2));

        List<Boat> boats = boatController.getAllBoats();

        assertNotNull(boats);
        assertEquals(2, boats.size());
    }

    @Test
    void getBoatById_BoatExists_ReturnsBoat() {
        Boat boat = new Boat();
        when(boatService.getBoatById(1L)).thenReturn(boat);

        Boat result = boatController.getBoatById(1L);

        assertNotNull(result);
    }

    @Test
    void getBoatById_BoatDoesNotExist_ReturnsNull() {
        when(boatService.getBoatById(1L)).thenReturn(null);

        Boat result = boatController.getBoatById(1L);

        assertNull(result);
    }

    @Test
    void addBoat_ValidBoat_ReturnsCreatedBoat() {
        Boat boat = new Boat();
        when(boatService.addBoat(boat)).thenReturn(boat);

        ResponseEntity<Boat> response = boatController.addBoat(boat);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(boat, response.getBody());
    }

    @Test
    void updateBoat_ValidBoat_ReturnsUpdatedBoat() {
        Boat boat = new Boat();
        when(boatService.updateBoat(1L, boat)).thenReturn(boat);

        Boat result = boatController.updateBoat(1L, boat);

        assertNotNull(result);
        assertEquals(boat, result);
    }

    @Test
    void deleteBoat_ValidId_ReturnsOkResponse() {
        doNothing().when(boatService).deleteBoat(1L);

        ResponseEntity<?> response = boatController.deleteBoat(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
package com.bernardo.boatchallenge.services;

import com.bernardo.boatchallenge.entities.Boat;
import com.bernardo.boatchallenge.repositories.BoatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoatServiceImplTest {

    @Mock
    private BoatRepository boatRepository;

    @InjectMocks
    private BoatServiceImpl boatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBoats_returnsListOfBoats() {
        Boat boat1 = new Boat();
        Boat boat2 = new Boat();
        when(boatRepository.findAll()).thenReturn(Arrays.asList(boat1, boat2));

        List<Boat> boats = boatService.getAllBoats();

        assertEquals(2, boats.size());
        verify(boatRepository, times(1)).findAll();
    }

    @Test
    void getBoatById_returnsBoat_whenBoatExists() {
        Boat boat = new Boat();
        when(boatRepository.findById(1L)).thenReturn(Optional.of(boat));

        Boat foundBoat = boatService.getBoatById(1L);

        assertNotNull(foundBoat);
        verify(boatRepository, times(1)).findById(1L);
    }

    @Test
    void getBoatById_throwsException_whenBoatDoesNotExist() {
        when(boatRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> boatService.getBoatById(1L));

        assertEquals("Boat not found: 1", exception.getMessage());
        verify(boatRepository, times(1)).findById(1L);
    }

    @Test
    void addBoat_savesAndReturnsBoat() {
        Boat boat = new Boat();
        when(boatRepository.save(boat)).thenReturn(boat);

        Boat savedBoat = boatService.addBoat(boat);

        assertNotNull(savedBoat);
        verify(boatRepository, times(1)).save(boat);
    }

    @Test
    void updateBoat_updatesAndReturnsUpdatedBoat() {
        Boat existingBoat = new Boat();
        Boat updatedBoat = new Boat();
        updatedBoat.setName("Updated Name");
        when(boatRepository.findById(1L)).thenReturn(Optional.of(existingBoat));
        when(boatRepository.save(existingBoat)).thenReturn(existingBoat);

        Boat result = boatService.updateBoat(1L, updatedBoat);

        assertEquals("Updated Name", result.getName());
        verify(boatRepository, times(1)).findById(1L);
        verify(boatRepository, times(1)).save(existingBoat);
    }

    @Test
    void deleteBoat_deletesBoat() {
        doNothing().when(boatRepository).deleteById(1L);

        boatService.deleteBoat(1L);

        verify(boatRepository, times(1)).deleteById(1L);
    }
}
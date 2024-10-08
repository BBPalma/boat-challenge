package com.bernardo.boatchallenge.services;

import com.bernardo.boatchallenge.entities.Boat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoatService {
    List<Boat> getAllBoats();
    Boat getBoatById(long id);
    Boat addBoat(Boat boat);
    Boat updateBoat(long id, Boat updatedBoat);
    void deleteBoat(Long id);
}

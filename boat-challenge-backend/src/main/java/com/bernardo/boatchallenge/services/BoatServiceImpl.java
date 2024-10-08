package com.bernardo.boatchallenge.services;

import com.bernardo.boatchallenge.entities.Boat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bernardo.boatchallenge.repositories.BoatRepository;

import java.util.List;

@Service
public class BoatServiceImpl implements BoatService {

    private final BoatRepository boatRepository;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    public List<Boat> getAllBoats() {
        return (List<Boat>) boatRepository.findAll();
    }

    public Boat getBoatById(long id) {
        //TODO return better exception
        return boatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boat not found: " + id));
    }

    public Boat addBoat(Boat boat) {
        return boatRepository.save(boat);
    }

    public Boat updateBoat(long id, Boat updatedBoat) {
        Boat existingBoat = getBoatById(id);
        existingBoat.setName(updatedBoat.getName());
        existingBoat.setDescription(updatedBoat.getDescription());
        existingBoat.setDate(updatedBoat.getDate());
        existingBoat.setLength(updatedBoat.getLength());
        return boatRepository.save(existingBoat);
    }

    public void deleteBoat(Long id) {
        boatRepository.deleteById(id);
    }
}


package com.bernardo.boatchallenge.controllers;

import com.bernardo.boatchallenge.entities.Boat;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.bernardo.boatchallenge.services.BoatService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boats")
public class BoatController {


    private final BoatService boatService;

    @Autowired
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @GetMapping
    public List<Boat> getAllBoats() {
        return boatService.getAllBoats();
    }

    @GetMapping("/{id}")
    public Boat getBoatById(@PathVariable Long id) {
        return boatService.getBoatById(id);
    }

    @PostMapping
    public ResponseEntity<Boat> addBoat(@Valid @RequestBody Boat boat) {
        Boat savedBoat = boatService.addBoat(boat);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBoat);
    }

    @PutMapping("/{id}")
    public Boat updateBoat(@PathVariable Long id, @Valid @RequestBody Boat updatedBoat) {
        return boatService.updateBoat(id, updatedBoat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoat(@PathVariable Long id) {
        boatService.deleteBoat(id);
        return ResponseEntity.ok().build();
    }
}

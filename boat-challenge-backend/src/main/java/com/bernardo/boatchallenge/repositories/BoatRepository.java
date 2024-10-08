package com.bernardo.boatchallenge.repositories;

import com.bernardo.boatchallenge.entities.Boat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatRepository extends CrudRepository<Boat, Long> {
}

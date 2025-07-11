package com.algofusion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algofusion.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}

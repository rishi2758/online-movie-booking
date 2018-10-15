package com.udaan.onlinebooking.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udaan.onlinebooking.dao.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}

package com.udaan.onlinebooking.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udaan.onlinebooking.dao.entity.Screen;
import com.udaan.onlinebooking.dao.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
	
	List<Seat> findByRowAndScreenId(String row,Screen screenId);
	List<Seat> findByScreenIdAndReserved(Screen screenId,boolean reserved);
	List<Seat> findByRowAndReserved(String row,boolean reserved);
	Seat findByScreenIdAndRowAndSeatNumberAndReserved(Screen screenId,String row,Integer seatNumber,boolean reserved);
	
	
}

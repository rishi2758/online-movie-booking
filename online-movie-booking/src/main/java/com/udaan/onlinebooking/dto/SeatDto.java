package com.udaan.onlinebooking.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SeatDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5127245095147753546L;
	
	Map<String,List<Integer>> seats;

	public Map<String, List<Integer>> getSeats() {
		return seats;
	}

	public void setSeats(Map<String, List<Integer>> seats) {
		this.seats = seats;
	}
	
}

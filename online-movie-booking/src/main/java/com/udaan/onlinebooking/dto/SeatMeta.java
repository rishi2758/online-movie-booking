package com.udaan.onlinebooking.dto;

import java.util.List;

public class SeatMeta {

	private Integer numberOfSeats;
	private List<Integer> aisleSeats;

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public List<Integer> getAisleSeats() {
		return aisleSeats;
	}

	public void setAisleSeats(List<Integer> aisleSeats) {
		this.aisleSeats = aisleSeats;
	}

	@Override
	public String toString() {
		return "SeatMeta [numberOfSeats=" + numberOfSeats + ", aisleSeats=" + aisleSeats + "]";
	}

	
}

package com.udaan.onlinebooking.dto;

import java.io.Serializable;
import java.util.Map;

public class ScreenInfoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8111710599786883742L;
	
	private Map<String, SeatMeta> seatInfo;
	private String name;

	public Map<String, SeatMeta> getSeatInfo() {
		return seatInfo;
	}

	public void setSeatInfo(Map<String, SeatMeta> seatInfo) {
		this.seatInfo = seatInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

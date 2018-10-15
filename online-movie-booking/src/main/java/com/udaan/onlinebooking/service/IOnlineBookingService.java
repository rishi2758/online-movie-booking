package com.udaan.onlinebooking.service;

import java.util.List;
import java.util.Map;

import com.udaan.onlinebooking.common.exception.InvalidAisleValuesException;
import com.udaan.onlinebooking.common.exception.InvalidBookingException;
import com.udaan.onlinebooking.dao.entity.User;
import com.udaan.onlinebooking.dto.ScreenInfoDto;
import com.udaan.onlinebooking.dto.SeatDto;

public interface IOnlineBookingService {

	public void addScreenInfo(ScreenInfoDto screenInfo) throws InvalidAisleValuesException;
	public SeatDto findAvailableSeats(String screenName,String status);
	public Map<String, List<Integer>> findOptimalSeatAllocation(String screenName,Integer numberOfSeats,String aroundSeat);
	boolean reserveSeats(String screenName, SeatDto reserveSeats, User user) throws InvalidBookingException;
}

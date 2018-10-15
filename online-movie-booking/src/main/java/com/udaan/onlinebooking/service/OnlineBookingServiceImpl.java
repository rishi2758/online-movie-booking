package com.udaan.onlinebooking.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udaan.onlinebooking.common.exception.InvalidAisleValuesException;
import com.udaan.onlinebooking.common.exception.InvalidBookingException;
import com.udaan.onlinebooking.dao.entity.Reservation;
import com.udaan.onlinebooking.dao.entity.Screen;
import com.udaan.onlinebooking.dao.entity.Seat;
import com.udaan.onlinebooking.dao.entity.User;
import com.udaan.onlinebooking.dao.repository.ReservationRepository;
import com.udaan.onlinebooking.dao.repository.ScreenRepository;
import com.udaan.onlinebooking.dao.repository.SeatRepository;
import com.udaan.onlinebooking.dao.repository.UserRepository;
import com.udaan.onlinebooking.dto.ScreenInfoDto;
import com.udaan.onlinebooking.dto.SeatDto;
import com.udaan.onlinebooking.dto.SeatMeta;

@Service
public class OnlineBookingServiceImpl implements IOnlineBookingService{

	@Autowired
	private ScreenRepository screenDao;
	
	@Autowired
	private SeatRepository seatDao;
	
	@Autowired 
	private ReservationRepository reservationDao;
	
	@Autowired
	private UserRepository userDao;
	
	@Override
	public void addScreenInfo(ScreenInfoDto screenInfo) throws InvalidAisleValuesException{

		Screen screen = screenDao.findByName(screenInfo.getName());
		
		if(screen == null) {
			screen = new Screen();
			screen.setName(screenInfo.getName());
		}
		
		List<Seat> seats = new ArrayList<>();
		
		for(Entry<String, SeatMeta> entry : screenInfo.getSeatInfo().entrySet()) {
			

			int totalNumberOfSeats = entry.getValue().getNumberOfSeats();
			boolean isAisle[] = new boolean[totalNumberOfSeats];
			
			List<Integer> aisleSeats=entry.getValue().getAisleSeats();
			
			int i = 0;
			while(i < aisleSeats.size()) {
				if(aisleSeats.get(i) < isAisle.length)
					isAisle[aisleSeats.get(i)]= true;
				else
					throw new InvalidAisleValuesException("Aisle Values Cannot Fall Outside the Range");
				++i;
			}
			
			for(int u = 0;u < isAisle.length ; u++) {
				Seat seat = new Seat();
				seat.setReserved(false);
				seat.setRow(entry.getKey());
				seat.setAisleSeat(isAisle[u]);
				seat.seatNumber(u);
				seat.setScreenId(screen);
				seats.add(seat);
				screen.setSeats(seats);
				screenDao.saveAndFlush(screen);
			}
			
		}
		
	}

	@Override
	public boolean reserveSeats(String screenName,SeatDto reserveSeats,User user) throws InvalidBookingException {
		
		user = userDao.findByUserNameAndPassword(user.getUserName(), user.getPassword());
		
		if(user == null)
			throw new InvalidBookingException("You are not authorized to perform the booking");
		
		HashMap<String,List<Integer>> toRSeats = (HashMap<String, List<Integer>>) reserveSeats.getSeats();
		Screen screen = screenDao.findByName(screenName);
		if(screen == null)
			throw new InvalidBookingException("screen doesnt exists");
		
		Reservation reservation = new Reservation();
		for(Entry<String,List<Integer>> entry : toRSeats.entrySet()) {
			for(Integer index : entry.getValue()) {
				Seat seats = seatDao.findByScreenIdAndRowAndSeatNumberAndReserved(screen, entry.getKey(), index, false);
				if(seats == null)
					throw new InvalidBookingException("Invalid Row Selected For Booking");
				else {
					
					if(alreadyBooked(seats.getId(),user.getReservations()))
						throw new InvalidBookingException("Youv'e already booked this ticket previously");
					
					seats.setReserved(true);
					List<Seat> ss = screen.getSeats();
					ss.add(seats);
					screen.setSeats(ss);
					reservation.setSeat(seats);
				}
			}
			screenDao.save(screen);
		}
		
		reservation.setUserReservationId(user);
		reservation.setScreenId(screen);
		reservation.setReservationDate(new Date());
		
		List<Reservation> reservations = user.getReservations();
		reservations.add(reservation);
		user.setReservations(reservations);
		
		return reservationDao.save(reservation) != null;
	}

	private boolean alreadyBooked(int id, List<Reservation> reservations) {

		for (Reservation reservation : reservations) {
			if(reservation.getSeat().getId() == id)
				return true;
		}

		return false;
	}

	@Override
	public SeatDto findAvailableSeats(String screenName,String status) {
		
		boolean seatStatus = false;
		
		if(status.equals("reserved"))
			seatStatus = true;
		
		List<Seat> unreservedSeats = seatDao.findByScreenIdAndReserved(screenDao.findByName(screenName),seatStatus);
		HashMap<String,List<Integer>> response = new HashMap<>();
		for(Seat seat : unreservedSeats) {
			if(!response.containsKey(seat.getRow())) {
				List<Integer> seats = new ArrayList<>();
				seats.add(seat.getSeatNumber());
				
				response.put(seat.getRow(),seats);
			}else {
				response.get(seat.getRow()).add(seat.getSeatNumber());
			}
		}
		
		SeatDto seatDto = new SeatDto();
		seatDto.setSeats(response);
		
		return seatDto;

	}
	@Override
	public HashMap<String,List<Integer>> findOptimalSeatAllocation(String screenName, Integer numberOfSeats, String aroundSeat) {

		HashMap<String,List<Integer>> response = new HashMap<>();
		String row = aroundSeat.substring(0, 1);
		int aroundIndex = Integer.parseInt(aroundSeat.substring(1,aroundSeat.length()));
		List<Seat> seats = seatDao.findByRowAndScreenId(row, screenDao.findByName(screenName));
		int n = seats.size();
		List<Integer> optimalSeats = new ArrayList<>();
		if (aroundIndex >= 0 && aroundIndex < n) {

			Seat seat = seats.get(aroundIndex);

			if(seat.isReserved()) {
				response.put(row,null);
				return response;
			}
			
			int startIndex = seat.getSeatNumber() - (numberOfSeats - 1);
			int endIndex = seat.getSeatNumber();
			
			if (startIndex == endIndex) {
				if (!seats.get(startIndex).isReserved()) {
					optimalSeats.add(seats.get(startIndex).getSeatNumber());
					response.put(row, optimalSeats);
				}else {
					response.put(row, null);
				}
				return response;
			}
			
			while(startIndex < endIndex) {
				
				if(startIndex < 0  || endIndex >= n) {
					optimalSeats = Collections.emptyList();
					break;
				}
				
				if(seats.get(startIndex).isAisleSeat() && seats.get(endIndex).isAisleSeat()) {
					if(seat.getSeatNumber() !=  endIndex) {
						optimalSeats = Collections.emptyList();
						break;
					}
				}
				
				if(!seats.get(startIndex).isReserved() && !seats.get(endIndex).isReserved() && (!seats.get(startIndex).isAisleSeat() || !seats.get(endIndex).isAisleSeat())) {
					optimalSeats.add(seats.get(startIndex).getSeatNumber());
					if(startIndex != endIndex)
					 optimalSeats.add(seats.get(endIndex).getSeatNumber());
					++startIndex;
					--endIndex;
				}else {
					optimalSeats = new ArrayList<>();
					startIndex = seat.getSeatNumber();
					endIndex = seat.getSeatNumber() + (numberOfSeats - 1);
				}
				
				
			}
			
		}
		
		response.put(row, optimalSeats);
		return response ;
	}

}

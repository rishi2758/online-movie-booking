package com.udaan.onlinebooking.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udaan.onlinebooking.common.exception.InvalidAisleValuesException;
import com.udaan.onlinebooking.common.exception.InvalidBookingException;
import com.udaan.onlinebooking.common.messages.Messages;
import com.udaan.onlinebooking.dao.entity.User;
import com.udaan.onlinebooking.dto.ResponseDto;
import com.udaan.onlinebooking.dto.ScreenInfoDto;
import com.udaan.onlinebooking.dto.SeatDto;
import com.udaan.onlinebooking.service.IOnlineBookingService;

@RestController
public class BookingController {

	@Autowired
	private IOnlineBookingService bookingService;
	
	@Value("${app.screens.apiKey}")
	private String secretKey;
	
	@PostMapping("/screens")
	public ResponseDto<Object> addScreenInfo(
			@RequestBody ScreenInfoDto bookingRequest,
			HttpServletRequest request) throws InvalidAisleValuesException, InvalidBookingException {

		String apiKey = request.getHeader("api-key");
		
		if (apiKey != null && !apiKey.isEmpty()) {
			if (apiKey.equals(secretKey)) {
				bookingService.addScreenInfo(bookingRequest);

				ResponseDto<Object> response = new ResponseDto<>();
				response.setCode(Messages.Success.getCode());
				response.setMessage(Messages.Success.getMessage());
				response.setResponse(null);
				return response;
			}else
				throw new InvalidBookingException("Your'e not Authorized to Use this resource"); 
		}
		else
			throw new InvalidBookingException("Please Provide Credentials to proceed!");
	}

	@PostMapping("/screens/{screenName}/reserve")
	public ResponseDto<Object> reserveSeat(
			@PathVariable("screenName") String screenName,
			@RequestBody SeatDto seatReservationRequest,
			HttpServletRequest request) throws InvalidAisleValuesException, InvalidBookingException {
		
		String userName = request.getHeader("username");
		String password = request.getHeader("password");
		
		if (userName != null && !userName.isEmpty() && password != null && !password.isEmpty()) {
			ResponseDto<Object> response = new ResponseDto<>();
			User user = new User();
			user.setUserName(userName);
			user.setPassword(password);

			bookingService.reserveSeats(screenName, seatReservationRequest, user);
			
			response.setCode(Messages.Success.getCode());
			response.setMessage(Messages.Success.getMessage()+" You Have Successfully Reserved Your Seats!");
			response.setResponse(null);
			return response;
		}else
			throw new InvalidBookingException("Please Provide Credentials to proceed!");
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/screens/{screenName}/seats",params= {"numSeats","choice"})
	public ResponseDto<Object> fetchOptimalLocation(
			@PathVariable String screenName,
			@RequestParam(value = "numSeats", required = true) Integer numSeats,
			@RequestParam(value = "choice", required = true) String choice,
			HttpServletRequest request) throws InvalidAisleValuesException {
		HashMap<String,Object> result = new HashMap<>();
		result.put("availableSeats", bookingService.findOptimalSeatAllocation(screenName, numSeats, choice));
		
		ResponseDto<Object> response = new ResponseDto<>();
		response.setCode(Messages.Success.getCode());
		response.setMessage(Messages.Success.getMessage());
		response.setResponse(result);
		return response;

	}
	
	@GetMapping("/screens/{screenName}/seats")
	public ResponseDto<Object> fetchUnreservedSeats(
			@PathVariable String screenName,
			@RequestParam(value = "status", required = true) String status,
			HttpServletRequest request) throws InvalidAisleValuesException {
		HashMap<String,Object> result = new HashMap<>();
		result.put("availableSeats", bookingService.findAvailableSeats(screenName,status));

		ResponseDto<Object> response = new ResponseDto<>();
		response.setCode(Messages.Success.getCode());
		response.setMessage(Messages.Success.getMessage());
		response.setResponse(result);
		return response;

	}
	
}

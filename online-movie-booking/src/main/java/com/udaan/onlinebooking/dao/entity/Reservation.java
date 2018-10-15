package com.udaan.onlinebooking.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int Id;

	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screenId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id")
	private Seat seat;
	
	@ManyToOne
	@JoinColumn(name = "user_reservation_id")
	private User userReservationId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reservation_date")
	private Date reservationDate;
	

	public User getUserReservationId() {
		return userReservationId;
	}

	public void setUserReservationId(User userReservationId) {
		this.userReservationId = userReservationId;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Screen getScreenId() {
		return screenId;
	}

	public void setScreenId(Screen screenId) {
		this.screenId = screenId;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
}

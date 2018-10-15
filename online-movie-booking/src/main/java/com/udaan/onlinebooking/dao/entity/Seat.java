package com.udaan.onlinebooking.dao.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "seat",uniqueConstraints={
	    @UniqueConstraint(columnNames = {"row", "seatNumber","screen_id"})
	})
public class Seat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int Id;

	private String row;
	private Integer seatNumber;
	private boolean reserved;
	private boolean isAisleSeat;

	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screenId;
	
	@OneToOne(mappedBy = "seat", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Reservation reservation;
	
	public Seat(int id, String row, Integer seatNumber, boolean reserved, boolean isAisleSeat, Screen screenId,
			Reservation reservation) {
		super();
		Id = id;
		this.row = row;
		this.seatNumber = seatNumber;
		this.reserved = reserved;
		this.isAisleSeat = isAisleSeat;
		this.screenId = screenId;
		this.reservation = reservation;
	}

	
	public Seat() {
		super();
	}


	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void seatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Screen getScreenId() {
		return screenId;
	}

	public void setScreenId(Screen screenId) {
		this.screenId = screenId;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public boolean isAisleSeat() {
		return isAisleSeat;
	}

	public void setAisleSeat(boolean isAisleSeat) {
		this.isAisleSeat = isAisleSeat;
	}

}

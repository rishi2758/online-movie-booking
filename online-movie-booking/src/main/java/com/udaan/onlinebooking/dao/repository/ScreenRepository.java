package com.udaan.onlinebooking.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udaan.onlinebooking.dao.entity.Screen;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {

	Screen findByName(String name);
	
}

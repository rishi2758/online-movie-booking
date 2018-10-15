package com.udaan.onlinebooking.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udaan.onlinebooking.dao.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserNameAndPassword(String userName,String password);
	
}

package com.cooksys.social_media.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.social_media.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByCredentials_Username(String username);

	Optional<User> findByCredentials_Username(String username);

	

}

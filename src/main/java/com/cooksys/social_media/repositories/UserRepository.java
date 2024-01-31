package com.cooksys.social_media.repositories;

import org.mapstruct.Mapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.social_media.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByDeletedFalse();

    Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

}

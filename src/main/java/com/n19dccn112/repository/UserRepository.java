package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Order;
import com.n19dccn112.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional <User> findAllByUsername(String user);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional <User> findByEmail(String email);

    @Query(value = "Select * from users where user_id = (SELECT MAX(user_id) FROM users WHERE username = ?1)", nativeQuery = true)
    Optional<User> findUserByUserName(String userName);
}

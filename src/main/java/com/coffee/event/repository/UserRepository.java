package com.coffee.event.repository;

import com.coffee.event.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUserEmail(String email);
}

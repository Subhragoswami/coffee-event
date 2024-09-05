package com.coffee.event.dao;

import com.coffee.event.entity.User;
import com.coffee.event.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserDao
{
    private final UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }
    public Optional<User> getById(UUID userId){
        return userRepository.findById(userId);
    }

    public Page<User> getAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public boolean existsByUserEmail(String email){
        return userRepository.existsByUserEmail(email);
    }
}

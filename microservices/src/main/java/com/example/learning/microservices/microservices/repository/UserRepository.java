package com.example.learning.microservices.microservices.repository;

import com.example.learning.microservices.microservices.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface UserRepository  extends MongoRepository<User, String> {
      List<User> findByUsername(String username);

}

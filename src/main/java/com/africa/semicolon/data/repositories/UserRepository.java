package com.africa.semicolon.data.repositories;

import com.africa.semicolon.data.model.Note;
import com.africa.semicolon.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

}

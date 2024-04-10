package com.africa.semicolon.services;

import com.africa.semicolon.data.model.User;
import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.request.RegisterUserRequest;
import com.africa.semicolon.dtos.request.UpdateUserRequest;
import com.africa.semicolon.dtos.response.DeleteResponse;
import com.africa.semicolon.dtos.response.RegisterUserResponse;
import com.africa.semicolon.dtos.response.UpdateUserResponse;
import com.africa.semicolon.exceptions.InvalidPasswordException;
import com.africa.semicolon.exceptions.UsernameAlreadyExistException;
import com.africa.semicolon.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.africa.semicolon.utils.Mapper.*;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        String username = registerUserRequest.getUsername();
        validate(username);
        User user = mapRegisterUser(registerUserRequest);
        User savedUser = userRepository.save(user);
        return mapRegisterUserResponse(savedUser);
    }

    public void validate(String username){
        for (User user : userRepository.findAll()){
            if (user.getUsername().equals(username)){
                throw new UsernameAlreadyExistException("Username already exist");
            }
        }
    }

    @Override
    public Long countUsers() {
        return userRepository.count();
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException(String.format("%s does not exist", username));
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public DeleteResponse deleteUser(String username) {
        User user = findByUsername(username);
        userRepository.delete(user);
        return mapDeleteUser(user);
    }

    @Override
    public UpdateUserResponse updateUser(String username, String password, UpdateUserRequest update) {
        User user = findByUsername(username);
        if (!(user.getPassword().equals(password))) { throw new InvalidPasswordException("Invalid password");}
        user.setUsername(update.getUsername());
        user.setFirstName(update.getFirstName());
        user.setLastName(update.getLastName());
        user.setUsername(update.getUsername());
        user.setPassword(update.getPassword());
        //User updateUser = mapUpdateUser(update);
        User savedUser = userRepository.save(user);

        UpdateUserResponse updateResponse = new UpdateUserResponse();
        updateResponse.setMessage("Updated successfully");
        return updateResponse;
    }
}

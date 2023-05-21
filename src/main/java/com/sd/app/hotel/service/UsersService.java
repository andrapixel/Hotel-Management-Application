package com.sd.app.hotel.service;

import com.sd.app.hotel.dto.LoginRequestDto;
import com.sd.app.hotel.model.LoginMessage;
import com.sd.app.hotel.model.UserEntity;

import java.util.List;

public interface UsersService {
    List<UserEntity> findAllUsers();
    UserEntity findById(long id);
    List<UserEntity> findByRole(String userRole);
    String saveUser(UserEntity newUserEntity);
    LoginMessage login(LoginRequestDto loginDto);
    String deleteUser(long id);
    String updateUser(long id, UserEntity givenUserEntity);
}
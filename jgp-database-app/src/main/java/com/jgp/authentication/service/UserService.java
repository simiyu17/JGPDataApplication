package com.jgp.authentication.service;

import com.jgp.authentication.domain.AppUser;
import com.jgp.authentication.dto.AuthRequestDto;
import com.jgp.authentication.dto.AuthResponseDto;
import com.jgp.authentication.dto.UserDto;
import com.jgp.authentication.dto.UserPassChangeDto;

import java.util.List;

public interface UserService {

    void createUser(UserDto user);

    void updateUser(Long userId, UserDto user);

    void updateUserPassword(UserPassChangeDto userPassChangeDto);

    UserDto findUserById(Long userId);

    AppUser findUserByUsername(String userName);

    AuthResponseDto authenticateUser(AuthRequestDto authRequestDto);

    List<UserDto> getAllUsers();

    AppUser currentUser();
}

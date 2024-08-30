package com.jgp.authentication.service;

import com.jgp.authentication.domain.AppUser;
import com.jgp.authentication.dto.AuthRequestDto;
import com.jgp.authentication.dto.AuthResponseDto;
import com.jgp.authentication.dto.UserDetailedDto;
import com.jgp.authentication.dto.UserDto;
import com.jgp.authentication.dto.UserPassChangeDto;

import java.util.List;

public interface UserService {

    void createUser(UserDetailedDto user);

    void updateUser(Long userId, UserDto user);

    void updateUserPassword(UserPassChangeDto userPassChangeDto);

    UserDetailedDto findUserById(Long userId);

    AppUser findUserByUsername(String userName);

    AuthResponseDto authenticateUser(AuthRequestDto authRequestDto);

    List<UserDetailedDto> getAllUsers();

    AppUser currentUser();
}

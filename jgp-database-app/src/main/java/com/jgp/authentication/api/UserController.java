package com.jgp.authentication.api;

import com.jgp.authentication.dto.UserDetailedDto;
import com.jgp.authentication.dto.UserDto;
import com.jgp.authentication.dto.UserPassChangeDto;
import com.jgp.authentication.service.UserService;
import com.jgp.shared.dto.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PutMapping("change-password")
    public ResponseEntity<ApiResponseDto> changePassword(@Valid @RequestBody UserPassChangeDto changePasswordDto){
        this.userService.updateUserPassword(changePasswordDto);
        return new ResponseEntity<>(new ApiResponseDto(true, "Password successfully updated !!"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDetailedDto>> getAvailableUsers(){
        return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createUser(@Valid @RequestBody UserDetailedDto newUser){
        this.userService.createUser(newUser);
        return new ResponseEntity<>(new ApiResponseDto(true, "User created !!"), HttpStatus.CREATED);
    }

    @PutMapping("{userId}")
    public ResponseEntity<ApiResponseDto> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserDto newUser){
        this.userService.updateUser(userId, newUser);
        return new ResponseEntity<>(new ApiResponseDto(true, "User updated !!"), HttpStatus.OK);
    }

    @PutMapping("{userId}/update-roles")
    public ResponseEntity<ApiResponseDto> updateUserRoles(@PathVariable("userId") Long userId, @Valid @RequestBody UserDto newUser){
        this.userService.updateUser(userId, newUser);
        return new ResponseEntity<>(new ApiResponseDto(true, "User updated !!"), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDetailedDto> getUser(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(this.userService.findUserById(userId), HttpStatus.OK);
    }
}

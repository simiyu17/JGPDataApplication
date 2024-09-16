package com.jgp.authentication.service;


import com.jgp.authentication.domain.AppUser;
import com.jgp.authentication.domain.AppUserRepository;
import com.jgp.authentication.dto.AuthRequestDto;
import com.jgp.authentication.dto.AuthResponseDto;
import com.jgp.authentication.dto.UserDetailedDto;
import com.jgp.authentication.dto.UserDto;
import com.jgp.authentication.dto.UserPassChangeDto;
import com.jgp.authentication.exception.UserNotAuthenticatedException;
import com.jgp.authentication.exception.UserNotFoundException;
import com.jgp.authentication.filter.JwtTokenProvider;
import com.jgp.patner.domain.PartnerRepository;
import com.jgp.patner.exception.PartnerNotFoundException;
import com.jgp.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final AppUserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final PartnerRepository partnerRepository;
    private final RoleService roleService;


    @Transactional
    @Override
    public void createUser(UserDetailedDto userDto) {
        try {
            final var partner = this.partnerRepository.findById(userDto.work().partnerId())
                    .orElseThrow(() -> new PartnerNotFoundException(CommonUtil.NO_RESOURCE_FOUND_WITH_ID));
            this.userRepository.save(AppUser.createUser(partner, userDto, passwordEncoder));
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

    }

    @Transactional
    @Override
    public void updateUser(Long userId, UserDto user) {
        var currentUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found with Id"));
        try {
            currentUser.updateUser(user);
            this.userRepository.save(currentUser);
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

    }

    @Transactional
    @Override
    public void updateUserPassword(UserPassChangeDto userPassChangeDto) {
        if (!StringUtils.equals(userPassChangeDto.newPass(), userPassChangeDto.passConfirm())){
            throw new UserNotAuthenticatedException("New password must match with confirmation password!!");
        }
        final var currentUser = currentUser();
        if (this.passwordEncoder.matches(userPassChangeDto.password(), currentUser.getPassword())){
            currentUser.setPassword(this.passwordEncoder.encode(userPassChangeDto.newPass()));
            currentUser.setForceChangePass(false);
        }else {
            throw new UserNotFoundException("Invalid current password!!");
        }
        this.userRepository.save(currentUser);
    }

    @Override
    public UserDetailedDto findUserById(Long userId) {
        return this.userRepository.findById(userId)
                .map(AppUser::toDto)
                .orElseThrow(() -> new UserNotFoundException("No user found with Id"));
    }

    @Override
    public AppUser findUserByUsername(String userName) {
        return this.userRepository.findByUsername(userName).orElse(null);
    }


    @Override
    public AuthResponseDto authenticateUser(AuthRequestDto authRequestDto) {
        final var userDetails = userDetailsService.loadUserByUsername(authRequestDto.username());
        if (Objects.isNull(userDetails)) {
            throw  new UserNotFoundException("Bad User Credentials !!");
        }else {
            final var user = findUserByUsername(userDetails.getUsername());
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails, authRequestDto.password(), userDetails.getAuthorities()));
            return new AuthResponseDto(true, "User Authenticated!!", JwtTokenProvider.createToken(user));
        }
    }

    @Override
    public List<UserDetailedDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(AppUser::toDto).toList();
    }

    @Override
    public AppUser currentUser() {
        AppUser currentUser = null;
        final var context = SecurityContextHolder.getContext();
        if (context != null) {
            final var authentication = context.getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                    String currentUserName = authentication.getName();
                    currentUser = this.findUserByUsername(currentUserName);
            }
        }
        return currentUser;
    }

    @Transactional
    @Override
    public void updateUserRoles(Long userId, List<String> roleNames) {
        final var user =  this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found with Id"));
        user.updateRoles(new HashSet<>(this.roleService.retrieveRolesByNames(roleNames)));
        this.userRepository.save(user);
    }


}

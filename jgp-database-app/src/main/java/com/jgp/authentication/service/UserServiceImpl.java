package com.jgp.authentication.service;


import com.jgp.authentication.domain.AppUser;
import com.jgp.authentication.domain.AppUserRepository;
import com.jgp.authentication.dto.AuthRequestDto;
import com.jgp.authentication.dto.AuthResponseDto;
import com.jgp.authentication.dto.UserDtoV2;
import com.jgp.authentication.dto.UserPassChangeDto;
import com.jgp.authentication.exception.PasswordChangeException;
import com.jgp.authentication.exception.UserNotAuthenticatedException;
import com.jgp.authentication.exception.UserNotFoundException;
import com.jgp.authentication.filter.JwtTokenProvider;
import com.jgp.patner.domain.Partner;
import com.jgp.patner.domain.PartnerRepository;
import com.jgp.patner.exception.PartnerNotFoundException;
import com.jgp.shared.exception.DataRulesViolationException;
import com.jgp.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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
    public void createUser(UserDtoV2 userDto) {
        try {
            Partner partner = null;
            if (Objects.nonNull(userDto.partnerId())) {
                partner = this.partnerRepository.findById(userDto.partnerId())
                        .orElseThrow(() -> new PartnerNotFoundException(CommonUtil.NO_RESOURCE_FOUND_WITH_ID));
            }
            var user = this.userRepository.save(AppUser.createUser(partner, userDto, passwordEncoder));
            if (!userDto.userRoles().isEmpty()){
                this.updateUserRoles(user.getId(), new ArrayList<>(userDto.userRoles()));
            }
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

    }

    @Transactional
    @Override
    public void updateUser(Long userId, UserDtoV2 userDto) {
        var currentUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found with Id"));
        Partner partner = null;
        if (Objects.nonNull(userDto.partnerId())) {
            partner = this.partnerRepository.findById(userDto.partnerId())
                    .orElseThrow(() -> new PartnerNotFoundException(CommonUtil.NO_RESOURCE_FOUND_WITH_ID));
        }
        try {
            currentUser.updateUser(userDto, partner);
            this.userRepository.save(currentUser);
            if (!userDto.userRoles().isEmpty()){
                this.updateUserRoles(userId, new ArrayList<>(userDto.userRoles()));
            }
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

    }

    @Transactional
    @Override
    public void updateUserPassword(UserPassChangeDto userPassChangeDto) {
        if (!StringUtils.equals(userPassChangeDto.newPass(), userPassChangeDto.passConfirm())){
            throw new DataRulesViolationException("New password must match with confirmation password!!");
        }
        final var passWordStrengthRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        boolean isValidPassword = Pattern.compile(passWordStrengthRegex)
                .matcher(userPassChangeDto.newPass())
                .find();
        if (!isValidPassword){
            throw new DataRulesViolationException("""
                    Invalid new password.  A password must:\s
                    1. Has minimum 8 characters in length. \s
                    2. Has at least one uppercase English letter (A-Z).
                    3. Has at least one lowercase English letter (a-z).
                    4. Has at least one digit (0-9) and\s
                    5. Has at least one special character (@#$%^&+=)
                   \s"""
            );
        }
        final var currentUser = currentUser();
        if (this.passwordEncoder.matches(userPassChangeDto.password(), currentUser.getPassword())){
            currentUser.setPassword(this.passwordEncoder.encode(userPassChangeDto.newPass()));
            currentUser.setForceChangePass(false);
        }else {
            throw new DataRulesViolationException("Invalid current password!!");
        }
        this.userRepository.save(currentUser);
    }

    @Override
    public UserDtoV2 findUserById(Long userId) {
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
    public List<UserDtoV2> getAllUsers() {
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

package com.nextgendevs.hanchor.service;

import com.nextgendevs.hanchor.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserLoginDetails(String userId);

    UserDto getUser(String userId);

    UserDto updateUser(String userId, UserDto userDto);

    List<UserDto> getUsers(int page, int limit, String queryParam);

    void deleteUser(String userId);

    boolean verifyEmailToken(String token);

    boolean requestPasswordReset(String email);

    boolean resetPassword(String token, String password);
}

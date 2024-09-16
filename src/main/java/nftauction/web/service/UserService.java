package nftauction.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import nftauction.web.dtos.UserDto;
import nftauction.web.dtos.UserRegisterDto;
import nftauction.web.enums.Role;
import nftauction.web.exceptions.InputErrorCode;
import nftauction.web.exceptions.InputException;
import nftauction.web.mapstructs.UserMapper;
import nftauction.web.model.AppUser;
import nftauction.web.repository.UserRepository;
import nftauction.web.security.JwtService;
import nftauction.web.security.common.CustomUserDetails;
import nftauction.web.security.common.CustomUserDetailsService;

@Service

public class UserService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  CustomUserDetailsService userDetailsService;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public void registerUser(UserRegisterDto userRegisterDto) {
    Optional<AppUser> userOpt = userRepository.findByUsername(userRegisterDto.getUsername());
    if (userOpt.isPresent()) {
      throw new InputException(InputErrorCode.USERNAME_ALREADY_EXISTS, "Username already exists");
    }
    AppUser user = UserMapper.INSTANCE.mapToUser(userRegisterDto);
    user.setRole(Role.USER);

    user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
    userRepository.save(user);
  }

  public String loginUser(String username, String password) {
    CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(username);
    if (!passwordEncoder.matches(password, customUserDetails.getPassword())) {
      throw new InputException(InputErrorCode.INVALID_CREDENTIALS, "Invalid username or password");
    }
    return jwtService.createAuthToken(customUserDetails);
  }


  public List<UserDto> getUsers() {
    List<AppUser> users = userRepository.findAll();
    return UserMapper.INSTANCE.mapToUserDtoList(users);
  }
}

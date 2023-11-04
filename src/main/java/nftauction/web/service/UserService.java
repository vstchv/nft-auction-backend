package nftauction.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nftauction.web.dtos.UserRegisterDto;
import nftauction.web.exceptions.InputErrorCode;
import nftauction.web.exceptions.InputException;
import nftauction.web.mapstructs.UserMapper;
import nftauction.web.model.User;
import nftauction.web.repository.UserRepository;

@Service

public class UserService {
  @Autowired
  UserRepository userRepository;

  public void registerUser(UserRegisterDto userRegisterDto) {
    User userOpt = userRepository.findByUsername(userRegisterDto.getUsername());
    if (userOpt != null) {
      throw new InputException(InputErrorCode.USERNAME_ALREADY_EXISTS, "Username already exists");
    }
    User user = UserMapper.INSTANCE.mapToUser(userRegisterDto);
    userRepository.save(user);
  }

  public User loginUser(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new InputException(InputErrorCode.USERNAME_NOT_FOUND, "Username not found");
    }
    if (!user.getPassword().equals(password)) {
      throw new InputException(InputErrorCode.WRONG_PASSWORD, "Wrong password");
    }
    return user;
  }
}

package nftauction.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nftauction.web.dtos.UserRegisterDto;
import nftauction.web.mapstructs.UserMapper;
import nftauction.web.model.User;
import nftauction.web.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;


  public void registerUser(UserRegisterDto userRegisterDto) {
    //TODO change to userDTO?
    User user = UserMapper.INSTANCE.mapToUser(userRegisterDto);
    userRepository.save(user);


  }
}

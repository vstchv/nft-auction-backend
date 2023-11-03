package nftauction.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nftauction.web.model.User;
import nftauction.web.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;


  public void registerUser(User user) {
    //TODO change to userDTO?
    userRepository.save(user);

  }
}

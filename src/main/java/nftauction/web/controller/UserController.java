package nftauction.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nftauction.web.model.User;
import nftauction.web.repository.UserRepository;
import nftauction.web.service.UserService;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;


  @RequestMapping(value = "api/users/register", method = RequestMethod.POST)
  public void registerUser(@RequestBody User user) {
    //TODO change to userDTO?
    userService.registerUser(user);
  }

}

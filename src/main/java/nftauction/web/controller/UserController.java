package nftauction.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nftauction.web.dtos.UserRegisterDto;
import nftauction.web.model.User;
import nftauction.web.repository.UserRepository;
import nftauction.web.service.UserService;

@RestController
@RequestMapping(value = "/")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;


  @RequestMapping(value = "api/users/register", method = RequestMethod.POST)
  public void registerUser(@RequestBody UserRegisterDto userRegisterDto) {
    //TODO change to userDTO?
    userService.registerUser(userRegisterDto);
  }

  @RequestMapping(value = "api", method = RequestMethod.GET)
  public String test() {
    return "Hello World";
  }

}

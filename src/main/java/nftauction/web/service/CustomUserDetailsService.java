package nftauction.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nftauction.web.model.User;
import nftauction.web.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
                              .orElseThrow(() -> new UsernameNotFoundException(
                                  "User not found with username: " + username));

    if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
      throw new UsernameNotFoundException("User " + username + " has no granted authorities");
    }

    return user;
  }
}

package nftauction.web.security.common;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nftauction.web.enums.Role;
import nftauction.web.model.AppUser;
import nftauction.web.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("Loading user by username=" + username);
    AppUser user = userRepository.findByUsername(username)
                                 .orElseThrow(() -> new UsernameNotFoundException(
                                     "User not found with username: " + username));

    if (user.getRole() == null) {
      throw new UsernameNotFoundException("User " + username + " has no granted authorities");
    }

    CustomUserDetails customUserDetails = new CustomUserDetails();
    customUserDetails.setPassword(user.getPassword());
    customUserDetails.setAuthorities(setAuthorities(user.getRole()));
    customUserDetails.setUsername(user.getUsername());
    customUserDetails.setEmail(user.getEmail());
    customUserDetails.setEnabled(user.isEnabled());
    customUserDetails.setAccountNonExpired(true);
    customUserDetails.setAccountNonLocked(true);
    customUserDetails.setCredentialsNonExpired(true);
    return customUserDetails;
    //    return CustomUserDetails.builder()
    //                            .user(user)
    //                            .build();
  }

  private Collection<GrantedAuthority> setAuthorities(Role role) {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

}

package nftauction.web.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    // Load user details using your custom UserDetailsService
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    if (passwordEncoder.matches(password, userDetails.getPassword())) {
      // If password matches, return an authenticated token with user details
      return new UsernamePasswordAuthenticationToken(userDetails, password,
                                                     userDetails.getAuthorities());
    } else {
      // If password does not match, throw an AuthenticationException
      throw new AuthenticationException("Invalid credentials") {
      };
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    // This provider supports UsernamePasswordAuthenticationToken
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}

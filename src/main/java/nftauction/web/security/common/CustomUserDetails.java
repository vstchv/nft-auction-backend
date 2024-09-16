package nftauction.web.security.common;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nftauction.web.model.AppUser;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

  protected AppUser user;
  protected String password;
  protected String username;
  protected String email;
  protected Collection<? extends GrantedAuthority> authorities;

  protected boolean accountNonExpired;
  protected boolean accountNonLocked;
  protected boolean credentialsNonExpired;
  protected boolean enabled;
}


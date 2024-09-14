package nftauction.web.security.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import nftauction.web.security.JwtService;
import nftauction.web.service.CustomUserDetailsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final CustomUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    logger.debug("Executing JwtAuthenticationFilter for request: " + request.getRequestURI());

    if (request.getServletPath().contains("/api/v1/auth")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;

    // Validate header
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      logger.debug("No JWT token found or invalid token.");
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authHeader.substring(7);
    logger.info("JWT token: " + jwt);
    userEmail = jwtService.extractUsername(jwt);

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

      if (userDetails.getAuthorities() == null || userDetails.getAuthorities().isEmpty()) {
        logger.error("Authorities are not set for user: " + userEmail);
        filterChain.doFilter(request, response);
      }

      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext()
                             .setAuthentication(authToken);  // Set authenticated user in context
        logger.info("Authenticated user set in SecurityContext: " + userEmail);
      } else {
        logger.warn("Invalid JWT token: " + jwt);
      }
    }

    filterChain.doFilter(request, response);
  }

}

package nftauction.web.security.filter;


import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nftauction.web.security.JwtService;
import nftauction.web.security.common.CustomUserDetails;
import nftauction.web.security.common.CustomUserDetailsService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final CustomUserDetailsService customUserDetailsService;

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

    if (request.getRequestURI().contains("api/users/login") || request.getRequestURI().contains(
        "api/users/register")) {
      logger.debug(request.getRequestURI() + " does not require authentication.");
      filterChain.doFilter(request, response);
      return;
    }

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
      CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userEmail);

      if (customUserDetails.getAuthorities() == null || customUserDetails.getAuthorities()
                                                                         .isEmpty()) {
        logger.error("Authorities are not set for user: " + userEmail);
        filterChain.doFilter(request, response);
      }

      if (jwtService.isTokenValid(jwt, customUserDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            customUserDetails,
            null,
            customUserDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext()
                             .setAuthentication(authToken);
        logger.info("Authenticated user set in SecurityContext: " + userEmail);
      } else {
        logger.warn("Invalid JWT token: " + jwt);
      }
    }

    filterChain.doFilter(request, response);
  }

}


// TODO Fix authorities and mapper again ? set proper claims on token
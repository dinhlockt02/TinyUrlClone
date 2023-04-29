package com.example.tinyurlclone.security;

import com.example.tinyurlclone.auth.model.RefreshToken;
import com.example.tinyurlclone.auth.repository.RefreshTokenRepository;
import com.example.tinyurlclone.auth.service.AuthService;
import com.example.tinyurlclone.common.JwtTokenService;
import com.example.tinyurlclone.common.ObjectID;
import com.example.tinyurlclone.common.UID;
import com.example.tinyurlclone.exception.NotFoundException;
import com.example.tinyurlclone.security.model.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtService;

    private final UserDetailsService userDetailsService;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getServletPath().contains("/api/v1/auth") || SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }


            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.trim().contains("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring("Bearer ".length());

            String id = jwtService.extractClaim(token, Claims::getSubject);

            if (id == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UID uid = new UID(id);

            if (uid.getObjectID() != ObjectID.USER) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails user = userDetailsService.loadUserByUsername(String.valueOf(uid.getLocalID()));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );



            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authToken);

            SecurityContextHolder.setContext(securityContext);

            filterChain.doFilter(request, response);


        } catch (UsernameNotFoundException exception) {
            filterChain.doFilter(request, response);
        }
    }
}

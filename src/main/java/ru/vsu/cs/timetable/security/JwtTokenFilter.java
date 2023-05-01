package ru.vsu.cs.timetable.security;

import com.auth0.jwt.exceptions.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vsu.cs.timetable.exception.AuthException;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = request.getHeader(AUTHORIZATION);
            if (token != null) {
                Authentication authentication = jwtTokenProvider.getAuthTokenFromJwt(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (AlgorithmMismatchException | TokenExpiredException |
                 SignatureVerificationException | MissingClaimException |
                 IncorrectClaimException e) {
            throw AuthException.CODE.JWT_VALIDATION_ERROR.get(e.getMessage());
        }
    }
}

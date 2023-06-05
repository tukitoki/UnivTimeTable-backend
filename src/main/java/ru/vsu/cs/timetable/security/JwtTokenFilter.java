package ru.vsu.cs.timetable.security;

import com.auth0.jwt.exceptions.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vsu.cs.timetable.exception.AuthException;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Slf4j
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
            String token = getTokenFromRequest(request);
            String servletPath = request.getServletPath();
            if (token != null) {
                Authentication authentication = jwtTokenProvider.getAuthTokenFromJwt(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (!servletPath.contains("swagger") && !servletPath.contains("api")) {
                log.warn("null token was sent");
            }

            filterChain.doFilter(request, response);
        } catch (AlgorithmMismatchException | TokenExpiredException |
                 SignatureVerificationException | MissingClaimException |
                 IncorrectClaimException e) {
            throw AuthException.CODE.JWT_VALIDATION_ERROR.get(e.getMessage());
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}

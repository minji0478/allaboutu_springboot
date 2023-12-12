package org.ict.allaboutu.config.filter;

import java.io.IOException;
import java.util.ArrayList;

import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.config.repository.TokenRepository;
import org.ict.allaboutu.config.service.JwtService;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    private static final int ACCESS_EXPIRED = 701;
    private static final int REFRESH_EXPIRED = 702;
    private static final int DOUBLE_EXPIRED = 703;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail; // username
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);// todo extract the userEmail from JWT token
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // userDetailsService.loadUserByUsername 대신 memberRepository.findByUserId 사용
            Member member = memberRepository.findByUserId(userEmail);
            boolean isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);

            if (jwtService.isTokenValid(jwt, member) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(member, null, new ArrayList<>());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
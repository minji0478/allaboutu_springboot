package org.ict.allaboutu.config;

import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.config.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;  // HttpMethod를 임포트 추가
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final LogoutHandler logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> anyList = List.of("/auth/**", "/index.html", "/signup", "/css/**", "/js/**", "/img/**", "/h2-console/**", "/favicon.ico", "/login", "/logout", "/api/**", "/assets/**",
                "/notices/search", "/notices/imp", "/notices/image/{renamefileName}", "/notices/detail/{noticeNum}", "/boards/search", "/boards/rank", "/boards/image/{imageName}",
                "/", "");
        List<String> userOnlyList = List.of("/style/upload", "/style/insert", "/style/image/{imageName}", "/member/{userId}", "/member/image/{imageName}", "/personal/upload", "/personal/insert", "/personal/image/{imageName}",
                "/boards/{boardNum}", "/boards/search", "/cody", "/cody/**");
        List<String> adminOnlyList = List.of("/reports/{reportNum}", "/member/{userNum}", "/reports/**", "/admin/get");

        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/notices", "/boards").permitAll()
                                .requestMatchers("/boards/{boardNum}", "/boards/{boardNum}/likes/{userNum}", "/boards/{boardNum}/comments").authenticated()
                                .requestMatchers(HttpMethod.POST, "/boards", "/boards/{boardNum}/reports", "/boards/{boardNum}/likes/{userNum}", "/boards/{boardNum}/comments").authenticated()
                                .requestMatchers(HttpMethod.POST, "/notices").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/notices/{noticeNum}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/boards/{boardNum}").access("@AccessService.isBoardAuthor(authentication, #boardNum)")
                                .requestMatchers(HttpMethod.DELETE, "/boards/{boardNum}/likes/{userNum}").access("@boardAccessService.isLikeOwner(authentication, #boardNum, #userNum)")
                                .requestMatchers(HttpMethod.DELETE, "/boards/{boardNum}/comments/{commentNum}").access("@boardAccessService.isCommentAuthor(authentication, #boardNum, #commentNum)")
                                .requestMatchers(HttpMethod.PATCH, "/notices/{noticeNum}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/boards/{boardNum}").access("@AccessService.isBoardAuthor(authentication, #boardNum)")
                                .requestMatchers(HttpMethod.PATCH, "/boards/{boardNum}/comments/{commentNum}").access("@AccessService.isCommentAuthor(authentication, #boardNum, #commentNum)")
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logoutConfig -> {
                    logoutConfig
                            .logoutUrl("/auth/logout")
                            .addLogoutHandler(logoutService);
                });

        return http.build();

    }
}

package org.ict.allaboutu.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.config.filter.JwtAuthenticationFilter;
import org.ict.allaboutu.member.domain.UserRole;
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
@Slf4j
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final LogoutHandler logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> anyList = List.of("/auth/**", "/index.html", "/signup", "/css/**", "/js/**", "/img/**", "/h2-console/**", "/favicon.ico", "/login", "/logout", "/api/**", "/assets/**",
                "/notices/search", "/notices/imp", "/notices/image/{renameFileName}", "/notices/download/{renameFileName}", "/notices/detail/{noticeNum}", "/boards/search", "/boards/rank", "/boards/image/{imageName}",
                "/", "/member/{userNum}", "/user_profile/**", "/member/image/{imageName}", "/style/image/{imageName}", "/personal/image/{imageName}", "/face/image/{imageName}");
        List<String> userOnlyList = List.of("/style/**", "/personal/**", "/boards/{boardNum}", "/cody/**", "/member/{userId}", "/", "/face/**");
        List<String> adminOnlyList = List.of("/reports/{reportNum}", "/reports/**", "/admin/get");

//        log.info("============UserRole.ADMIN.name() : " + UserRole.ADMIN.name());
//        log.info("============UserRole.ADMIN.name() : " + UserRole.USER.name());

        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/boards/{boardNum}", "/boards/{boardNum}/likes/{userNum}", "/boards/{boardNum}/comments").authenticated()
                                .requestMatchers(HttpMethod.POST, "/boards", "/boards/{boardNum}/reports", "/boards/{boardNum}/likes/{userNum}", "/boards/{boardNum}/comments").authenticated()
                                .requestMatchers(HttpMethod.POST, "/notices").hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/notices/{noticeNum}").hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/boards/{boardNum}").access("@AccessService.isBoardAuthor(authentication, #boardNum)")
                                .requestMatchers(HttpMethod.DELETE, "/boards/{boardNum}/likes/{userNum}").access("@AccessService.isLikeOwner(authentication, #boardNum, #userNum)")
                                .requestMatchers(HttpMethod.DELETE, "/boards/{boardNum}/comments/{commentNum}").access("@AccessService.isCommentAuthor(authentication, #boardNum, #commentNum)")
                                .requestMatchers(HttpMethod.PATCH, "/boards/{boardNum}").access("@AccessService.isBoardAuthor(authentication, #boardNum)")
                                .requestMatchers(HttpMethod.PATCH, "/boards/{boardNum}/comments/{commentNum}").access("@AccessService.isCommentAuthor(authentication, #boardNum, #commentNum)")
                                .requestMatchers(HttpMethod.PATCH, "/notices/{noticeNum}").hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(anyList.toArray(new String[0])).permitAll()
                                .requestMatchers(userOnlyList.toArray(new String[0])).hasAnyAuthority(UserRole.USER.name())
                                .requestMatchers(adminOnlyList.toArray(new String[0])).hasAuthority(UserRole.ADMIN.name())
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

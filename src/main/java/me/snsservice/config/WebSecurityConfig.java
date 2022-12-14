package me.snsservice.config;

import lombok.RequiredArgsConstructor;
import me.snsservice.auth.service.JwtTokenProvider;
import me.snsservice.config.security.JwtAccessDeniedHandler;
import me.snsservice.config.security.JwtAuthenticationEntryPoint;
import me.snsservice.config.security.JwtSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/h2-console/**",
                "/favicon.ico", "/error");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()

                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }

}

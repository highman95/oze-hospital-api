package com.oze.hospitalmanager.security;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import com.oze.hospitalmanager.models.Staff;
import com.oze.hospitalmanager.repositories.IStaffRepository;

@Configuration
@PropertySource("classpath:application.properties")
@EnableWebSecurity
public class SecurityConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Value("${app.http.auth.token-name}")
    private String authUUIDname;

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http, IStaffRepository staffRepository) throws Exception {
        PreAuthTokenHeaderFilter filter = new PreAuthTokenHeaderFilter(authUUIDname);
        filter.setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                try {
                    final String uuid = (String) authentication.getPrincipal();
                    Staff staff = staffRepository.findByUuid(UUID.fromString(uuid));
                    if (staff == null) {
                        throw new BadCredentialsException("Token is invalid");
                    }

                    authentication.setAuthenticated(true);
                } catch (Exception e) {
                    LOGGER.error("on-authenticating-error --> {}", e.getMessage());
                }

                return authentication;
            }
        });

        http.authorizeHttpRequests().antMatchers("/h2-console/**", "/h2/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/staffs").permitAll()
                .and()
                .addFilter(filter)
                .addFilterBefore(new ExceptionTranslationFilter(new Http403ForbiddenEntryPoint()),
                        filter.getClass())
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
        return http.build();
    }

}

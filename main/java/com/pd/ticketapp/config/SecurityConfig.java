package com.pd.ticketapp.config;

import com.pd.ticketapp.filter.UserProvisioningFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import com.pd.ticketapp.config.JwtAuthenticationConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            // Order Matters here apparently (filter chaining)
            // order does not matter syntactically (you can chain methods in any way)
            HttpSecurity httpSecurity,
            UserProvisioningFilter userProvisioningFilter,
    JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.GET,"/api/v1/published-events").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/published-events/**").permitAll()
                                .requestMatchers("/api/v1/events").hasRole("ORGANIZER")// Spring automatically adds ROLE_
                                .requestMatchers("/api/v1/ticket-validations").hasRole("STAFF")
                                .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2->
                        oauth2.jwt(jwt->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class);
        // use filter after when you need specific filters to be processed beforehand
        return httpSecurity.build();
    }
}

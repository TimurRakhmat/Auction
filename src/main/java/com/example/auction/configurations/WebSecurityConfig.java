package com.example.auction.configurations;

import com.example.auction.security.MainAuthFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MainAuthFilter mainAuthFilter;

    public WebSecurityConfig(MainAuthFilter mainAuthFilter) {
        this.mainAuthFilter = mainAuthFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Cache-Control", "Content-Type", "x-access-login", "x-access-password"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));

        httpSecurity
                .csrf()
                .disable()
                .sessionManagement()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/api/registration/**").permitAll()
                .antMatchers("/api/lots/**").permitAll()
                .antMatchers("/api/bets/**").permitAll()
                //.antMatchers("/api/bets/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(
                        mainAuthFilter.setRequireAuthMatcher(
                                List.of(
                                        new AndRequestMatcher(new AntPathRequestMatcher("/api/profile/**")),
                                        new AndRequestMatcher(new AntPathRequestMatcher("/api/lots/private/**")),
                                        new AndRequestMatcher(new AntPathRequestMatcher("/api/bets/private/**"))
//                                        new AndRequestMatcher(new AntPathRequestMatcher("/api/private/**")),
//                                        new AndRequestMatcher(new AntPathRequestMatcher("/api/private/**"))
                                )
                        ),
                        UsernamePasswordAuthenticationFilter.class
                ).cors().configurationSource(request -> corsConfiguration);
    }
}

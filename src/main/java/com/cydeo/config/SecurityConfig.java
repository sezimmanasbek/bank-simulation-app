package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig{

    private final SecurityService service;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService service, AuthSuccessHandler authSuccessHandler) {
        this.service = service;
        this.authSuccessHandler = authSuccessHandler;
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/accounts/**").hasAuthority("Admin")
                .antMatchers("/transactions/**").hasAnyAuthority("Admin","Cashier")
                .antMatchers("/","login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .successHandler(authSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and().rememberMe().tokenValiditySeconds(300)
                .key("token")
                .userDetailsService(service)
                .and().build();
    }


}

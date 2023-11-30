package ar.edu.unnoba.concursomateriales.config;

import ar.edu.unnoba.concursomateriales.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class UserSecurityConfig {

    private final UserService userService;

    @Autowired
    public UserSecurityConfig(@Lazy UserService adminService) {
        this.userService = adminService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .userDetailsService(userService)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/webjars/", "/resources/","/css/**").permitAll()
                        .requestMatchers("/**").permitAll()
                )
                .formLogin((form) -> form
                        .usernameParameter("email")
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

}

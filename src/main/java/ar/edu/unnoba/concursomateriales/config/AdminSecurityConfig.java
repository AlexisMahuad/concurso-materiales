package ar.edu.unnoba.concursomateriales.config;

import ar.edu.unnoba.concursomateriales.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@Order(1)
public class AdminSecurityConfig {

    private final AdministratorService adminService;

    @Autowired
    public AdminSecurityConfig(@Lazy AdministratorService adminService) {
        this.adminService = adminService;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/").setViewName("admin/home");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .userDetailsService(adminService)
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().hasAuthority("ROLE_ADMIN")
                )
                .formLogin((form) -> form
                        .usernameParameter("email")
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/home")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

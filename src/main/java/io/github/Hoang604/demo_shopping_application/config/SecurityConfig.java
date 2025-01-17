package io.github.Hoang604.demo_shopping_application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import io.github.Hoang604.demo_shopping_application.service.MyUserDetailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    public SecurityConfig(MyUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(customizer->customizer.disable())
                .authorizeHttpRequests(request -> request
                    .requestMatchers("/","/login", "/logout", "/register", "/home", "/images/logo/**").permitAll()
                    .anyRequest().permitAll())
                    // .anyRequest().authenticated())
                // .formLogin(formLogin -> formLogin
                //     .loginPage("/login")
                //     .defaultSuccessUrl("/home")
                //     .failureUrl("/login?error=true")
                //     .permitAll()
                // )
                // .oauth2Login(oauth2 -> oauth2
                //     .loginPage("/login")
                //     .failureUrl("/login?error=true")
                //     .userInfoEndpoint(userInfo -> userInfo
                //     .oidcUserService(oidcUserService()))
                //     .permitAll()    
                // )
                .httpBasic(Customizer.withDefaults())
                // .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                );
        return http.build();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }
    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }
}
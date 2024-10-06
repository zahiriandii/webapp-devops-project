package mk.finki.ukim.webappgymspringboot.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserNameAndPasswordAuthenticationProvider provider;
    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserNameAndPasswordAuthenticationProvider provider) {
        this.passwordEncoder = passwordEncoder;
        this.provider = provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests)-> requests
                        .requestMatchers("/","/products","logIn","logOut")
                        .permitAll()
                        .requestMatchers("/delete/**","/edit-form/**","/add-form/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/logIn")
                        .permitAll()
                        .failureUrl("/login?error=BadCredentials")
                        .defaultSuccessUrl("/products")
                )
                .logout((logout)->logout
                        .logoutUrl("/logOut")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/logIn")
                )
                .exceptionHandling((ex) -> ex.accessDeniedPage("/access-denied"));

        return http.build();
    }
    //@Bean
    public UserDetailsService userDetailsService ()
    {
        UserDetails user1 =
                User.builder()
                        .username("Andi")
                        .password(passwordEncoder.encode("12345"))
                        .roles("USER")
                        .build();

        UserDetails admin =
                User.builder()
                        .username("Admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user1,admin);
    }

    @Bean
    public AuthenticationManager authManager (HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(provider);
        return authenticationManagerBuilder.build();
    }
}

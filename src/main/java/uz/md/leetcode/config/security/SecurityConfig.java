package uz.md.leetcode.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {

    private final MyEntryPointHandler myEntryPointHandler;
    private final JwtFilter jwtFilter;

    public SecurityConfig(MyEntryPointHandler myEntryPointHandler,
                          JwtFilter jwtFilter) {
        this.myEntryPointHandler = myEntryPointHandler;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .antMatchers(RestConstants.OPEN_PAGES)
                                        .permitAll()
                                        .antMatchers(HttpMethod.OPTIONS)
                                        .permitAll()
                                        .antMatchers("/favicon.ico",
                                                "//*.png",
                                                "//*.gif",
                                                "//*.svg",
                                                "//*.jpg",
                                                "//*.html",
                                                "//*.css",
                                                "//*.js",
                                                "/swagger-ui.html",
                                                "/swagger-resources/",
                                                "/v2/",
                                                "/csrf",
                                                "/webjars/",
                                                "/v2/api-docs",
                                                "/configuration/ui")
                                        .permitAll()
                                        .antMatchers("/api/**")
                                        .authenticated())
                .exceptionHandling()
                .authenticationEntryPoint(myEntryPointHandler)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

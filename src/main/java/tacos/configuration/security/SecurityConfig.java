package tacos.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tacos.persistence.repository.UserRepository;

import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.cors.CorsConfiguration.ALL;
import static tacos.domain.Roles.USER;

@Configuration
public class SecurityConfig {
    @Value("${cors.origin.patterns}")
    private List<String> corsOriginPatterns;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return username -> repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(this::headersConfiguration)
                .cors(this::corsConfiguration)
                .authorizeHttpRequests(this::requestMatcherRegistry)
                .formLogin(this::loginFormConfiguration)
                .oauth2ResourceServer(this::resourceServerConfiguration)
                .build();
    }

    private void headersConfiguration(HeadersConfigurer<HttpSecurity> configurer) {
        configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
    }

    private void corsConfiguration(CorsConfigurer<HttpSecurity> configurer) {
        configurer.configurationSource(corsConfigurationSource(corsOriginPatterns));
    }

    private void requestMatcherRegistry(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize
    ) {
        authorize
                .requestMatchers("/design", "/orders").hasRole(USER.name())
                .requestMatchers(POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients")
                .requestMatchers(POST, "/api/tacos").hasAuthority("SCOPE_writeIngredients")
                .requestMatchers(PUT, "/api/orders/*").hasAuthority("SCOPE_writeIngredients")
                .requestMatchers(PATCH, "/api/orders/*").hasAuthority("SCOPE_writeIngredients")
                .requestMatchers(DELETE, "/api/ingredients/*").hasAuthority("SCOPE_deleteIngredients")
                .requestMatchers(DELETE, "/api/orders/*").hasAuthority("SCOPE_deleteIngredients")
                .anyRequest().permitAll();
    }

    private void loginFormConfiguration(FormLoginConfigurer<HttpSecurity> configurer) {
        configurer.loginPage("/login").permitAll().defaultSuccessUrl("/design");
    }

    private void resourceServerConfiguration(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        configurer.jwt(Customizer.withDefaults());
    }

    private CorsConfigurationSource corsConfigurationSource(List<String> originPatterns) {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(originPatterns);
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of(ALL));
        configuration.setAllowedMethods(List.of(ALL));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

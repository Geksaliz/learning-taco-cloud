package tacos.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import tacos.persistence.repository.UserRepository;

import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.cors.CorsConfiguration.ALL;
import static tacos.domain.Roles.USER;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Value("${cors.origin.patterns}")
    private List<String> corsOriginPatterns;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository repository) {
        return repository::findByUsername;
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            ReactiveUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        UserDetailsRepositoryReactiveAuthenticationManager manager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        manager.setPasswordEncoder(passwordEncoder);
        return manager;
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(this::corsConfiguration)
                .authorizeExchange(this::requestMatcherRegistry)
                .formLogin(this::loginFormConfiguration)
                .oauth2ResourceServer(this::resourceServerConfiguration)
                .build();
    }

    private void corsConfiguration(ServerHttpSecurity.CorsSpec configurer) {
        configurer.configurationSource(corsConfigurationSource(corsOriginPatterns));
    }

    private void requestMatcherRegistry(
            ServerHttpSecurity.AuthorizeExchangeSpec authorize
    ) {
        authorize
                .pathMatchers("/design", "/orders").hasRole(USER.name())
                .pathMatchers(POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients")
                .pathMatchers(POST, "/api/tacos").hasAuthority("SCOPE_writeIngredients")
                .pathMatchers(PUT, "/api/orders/*").hasAuthority("SCOPE_writeIngredients")
                .pathMatchers(PATCH, "/api/orders/*").hasAuthority("SCOPE_writeIngredients")
                .pathMatchers(DELETE, "/api/ingredients/*").hasAuthority("SCOPE_deleteIngredients")
                .pathMatchers(DELETE, "/api/orders/*").hasAuthority("SCOPE_deleteIngredients")
                .pathMatchers("/actuator/health", "/actuator/info").permitAll()
                .pathMatchers("/actuator/**").authenticated()
                .anyExchange().permitAll();
    }

    private void loginFormConfiguration(ServerHttpSecurity.FormLoginSpec configurer) {
        configurer.loginPage("/login");
    }

    private void resourceServerConfiguration(ServerHttpSecurity.OAuth2ResourceServerSpec configurer) {
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

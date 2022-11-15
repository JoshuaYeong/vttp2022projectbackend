package vttp2022.finalprojectbackend.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vttp2022.finalprojectbackend.filters.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    // @Autowired
    // private RSAKey rsaKey;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception {
        return httpSec
                .cors().and().csrf().disable()
                .authorizeRequests(auth -> auth.mvcMatchers("/signin", "/register", "/", "/fonts/**", "/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling().and()
                // .exceptionHandling((ex) -> ex.authenticationEntryPoint(new
                // BearerTokenAuthenticationEntryPoint())
                // .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // @Bean
    // public AuthenticationManager authManager(UserDetailsService
    // userDetailsService) {

    // DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    // authProvider.setUserDetailsService(userDetailsService);
    // authProvider.setPasswordEncoder(encoder());
    // return new ProviderManager(authProvider);
    // }

    // @Bean
    // public JWKSource<SecurityContext> jwkSource() {
    // rsaKey = Jwks.generateRsa();
    // JWKSet jwkSet = new JWKSet(rsaKey);
    // return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    // }

    // @Bean
    // JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
    // return new NimbusJwtEncoder(jwks);
    // }

    // @Bean
    // JwtDecoder jwtDecoder() throws JOSEException {
    // return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    // }
}

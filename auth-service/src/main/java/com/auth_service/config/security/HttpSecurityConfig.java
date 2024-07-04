package com.auth_service.config.security;

import com.auth_service.util.RolePermision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter)
            throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(getAuthorizationManagerRequestMatcherRegistryCustomizer())
                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getAuthorizationManagerRequestMatcherRegistryCustomizer() {
        return authorizeRequest -> {
            authorizeRequest.requestMatchers(HttpMethod.POST,"/user/register").permitAll();
            authorizeRequest.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
            authorizeRequest.requestMatchers(HttpMethod.POST,"/auth/validate-token").permitAll();
            authorizeRequest.requestMatchers(HttpMethod.POST,"/auth/log-out").permitAll();

            authorizeRequest.requestMatchers(HttpMethod.POST, "/post/create").hasAuthority(RolePermision.CREATE_ONE_.name());

            authorizeRequest.requestMatchers(HttpMethod.POST, "/post/update").hasAuthority(RolePermision.UPDATE_ONE_.name());

            authorizeRequest.requestMatchers(HttpMethod.GET,"/post").hasAuthority(RolePermision.READ_ALL_.name());

            authorizeRequest.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/products/[0-9]*"))
                    .hasAuthority(RolePermision.READ_ONE_.name());

            authorizeRequest.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.DELETE, "/products/[0-9]*"))
                    .hasAuthority(RolePermision.DELETE_ONE_.name());


            authorizeRequest.anyRequest().authenticated();
        };
    }
    /*
           //anotamos esta clase con @EnableMethodSecurity(prePostEnabled = true)
        //luego en el controller, cada metodo lo anotamos con @PreAutorize(hasRole(ADMINISTRADOR))
        //SI ES CON Permission seria PreAutorize(hasAuthority(lista de permisos))
        //    @PreAuthorize("permitAll()")
        //    @PreAuthorize("denyAll()")
        
        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:4200"));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
     */
    
    


}

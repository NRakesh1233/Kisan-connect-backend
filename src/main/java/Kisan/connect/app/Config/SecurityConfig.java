package Kisan.connect.app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	
	
	
	
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final CustomUserDetailsService customUserDetailsService;
	
	private final PasswordEncoder passwordEncoder;


	
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
			CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
				this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.customUserDetailsService = customUserDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		
		http
		.cors(cors->{})
		 .csrf(csrf -> csrf.disable())
		
	.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	.authorizeHttpRequests(auth->auth
			.requestMatchers(
					  "/swagger-ui/**",
                      "/v3/api-docs/**",
                      "/public/**",
                      "/uploads/**"
					).permitAll()

            .requestMatchers("/farmer/**")
            .hasAuthority("FARMER")

            .requestMatchers("/customer/**")
            .hasAuthority("CUSTOMER")

            .requestMatchers("/admin/**")
            .hasAuthority("ADMIN")

            .anyRequest()
            .authenticated())
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}

	@Bean
	public  AuthenticationProvider authenticationProvider() {
		
		
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(customUserDetailsService);
		
		provider.setPasswordEncoder(passwordEncoder);
	
		
		return provider;
	}
	
	

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();

    }

	
	
	
	
	
	
	
	
	
}

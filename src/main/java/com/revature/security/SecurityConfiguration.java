package com.revature.security;

import com.revature.security.props.CorsConfigurationProps;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Component
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final TokenPresentFilter tokenPresentFilter;

	private final CorsConfigurationProps corsConfigurationProps;

	public SecurityConfiguration(CorsConfigurationProps corsConfigurationProps, TokenPresentFilter tokenPresentFilter) {
        this.tokenPresentFilter = tokenPresentFilter;
		this.corsConfigurationProps = corsConfigurationProps;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers(HttpMethod.GET, "/actuator/*");
		web.ignoring().mvcMatchers(HttpMethod.GET, "/h2-console/*");
		web.ignoring().mvcMatchers(HttpMethod.POST, "/h2-console/*");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors(spec -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(corsConfigurationProps.getAllowedOrigins());
			config.setAllowedMethods(corsConfigurationProps.getAllowedMethods());
			config.setAllowedHeaders(corsConfigurationProps.getAllowedHeaders());
			config.setExposedHeaders(corsConfigurationProps.getExposedHeaders());
			config.setAllowCredentials(corsConfigurationProps.isAllowCredentials());
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", config);
			spec.configurationSource(source);
		}).csrf().disable()
		.httpBasic().disable()

		.formLogin().disable()
		.authorizeRequests()
			.antMatchers("/api/user/testNoAuth").permitAll()
			.anyRequest().authenticated()
		.and().addFilterAt(tokenPresentFilter, UsernamePasswordAuthenticationFilter.class)
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
}

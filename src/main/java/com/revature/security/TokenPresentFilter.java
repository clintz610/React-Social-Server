package com.revature.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.revature.models.User;

@Component
public class TokenPresentFilter extends OncePerRequestFilter {

	private SecurityService securityService;

	public TokenPresentFilter(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		authorize(request);
		filterChain.doFilter(request, response);
	}

	/* Verifies Token from request header through firebase, and sets the authority for requests
	 This filter is automatically applied to every request */

	private void authorize(HttpServletRequest request) {
		String token = securityService.getBearerToken(request);
		if (token != null) {
			FirebaseToken decodedToken = null;
			try {
				decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
				User user = firebaseTokenToUserDto(decodedToken);
				if (user != null) {
					System.out.println(user.toString());
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, null);
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(auth);
				}	
			} catch (FirebaseAuthException e) {
				e.printStackTrace();
			}

			User user = firebaseTokenToUserDto(decodedToken);
			if(user != null) {
				System.out.println(user.toString());
			}
		}

	}

	private User firebaseTokenToUserDto(FirebaseToken decodedToken) {
		User user = null;
		if (decodedToken != null) {
			user = new User();
			user.setUid(decodedToken.getUid());
			user.setEmail(decodedToken.getEmail());
		}
		return user;
	}

}


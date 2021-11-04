package com.revature.security;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityService {

    /* Parses token from header and returns just the required portion needed. */

    public String getBearerToken(HttpServletRequest request) {

        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7, authorization.length());
        } else {
        	bearerToken = authorization;
        }
        return bearerToken;
    }
}

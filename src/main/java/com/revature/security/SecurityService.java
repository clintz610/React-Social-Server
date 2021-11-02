package com.revature.security;

import com.revature.models.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.spi.FileSystemProvider;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityService {

    public String getBearerToken(HttpServletRequest request) {
//    	
//    	System.out.println("******");
//    	System.out.println("******");
//    	System.out.println("******");
//        Enumeration<String> headerNames = request.getHeaderNames();
//
//        if (headerNames != null) {
//                while (headerNames.hasMoreElements()) {
//                        System.out.println("Header: " + headerNames.nextElement());
//                }
//        }
//    	System.out.println("******");
//    	System.out.println("******");
//    	System.out.println("******");
//    	
//    	System.out.println("########");
//    	System.out.println("########");
//    	System.out.println("########");
//    	System.out.println("Token = " + request.getHeader("Authorization"));
//    	System.out.println("########");
//    	System.out.println("########");
//    	System.out.println("########");
//    	
//    	
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

package com.revature.security.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

//    private final FirebaseAuth firebaseAuth;
//
//    public FirebaseConfig() {
//        try {
//            Resource serviceAccount = new ClassPathResource("firebase_config.json");
//            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).build();
//            this.firebaseAuth = FirebaseAuth.getInstance(FirebaseApp.initializeApp(options));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Primary
    @Bean
    public void firebaseInit() throws IOException {
        Resource serviceAccount = new ClassPathResource("firebase_config.json");
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).build();
//        this.firebaseAuth = FirebaseAuth.getInstance(FirebaseApp.initializeApp(options));
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}

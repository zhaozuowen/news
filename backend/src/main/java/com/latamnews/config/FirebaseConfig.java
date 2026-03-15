package com.latamnews.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FirebaseConfig {
    public FirebaseConfig(AppProperties properties) {
        if (!properties.firebase().enabled() || properties.firebase().serviceAccountPath() == null || properties.firebase().serviceAccountPath().isBlank()) {
            log.info("Firebase disabled or service account path not provided. Using placeholder notification mode.");
            return;
        }
        try (FileInputStream serviceAccount = new FileInputStream(properties.firebase().serviceAccountPath())) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException ex) {
            log.warn("Unable to initialize Firebase from {}: {}", properties.firebase().serviceAccountPath(), ex.getMessage());
        }
    }
}

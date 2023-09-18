package com.example.Donation.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/donation-21c1e-firebase-adminsdk-euymx-8a49dfd3f5.json");// Firebase Admin SDK JSON 파일 경로

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://donation-21c1e-default-rtdb.firebaseio.com") // Firebase Realtime Database URL
                .build();

        FirebaseApp.initializeApp(options);
    }
}

package com.example.Gachon_Its.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class WebParsingService {

    public String readUrlToString(String urlToRead) {

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlToRead);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException ignore) {
            return null;
        }
        return result.toString();
    }
}

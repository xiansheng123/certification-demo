package com.example.democlient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@RestController
public class Controller {

    private final RestTemplate restTemplate;

    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("test")
    String demoForClientWithTrustStore() {
        System.out.println("test client http");
        return callApi();
    }

    String callApi() {
        String urlStr = "https://localhost:8443/server/test";
        return restTemplate.getForObject(urlStr, String.class);
    }

    String callApiWithoutCert() {
        String urlStr = "https://localhost:8443/server/test";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(urlStr, String.class);
    }
}



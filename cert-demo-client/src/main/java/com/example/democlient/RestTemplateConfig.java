package com.example.democlient;


import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

@Configuration
public class RestTemplateConfig {
    @Value("${http.client.ssl.trust-store}")
    private Resource keyStore;
    @Value("${http.client.ssl.trust-store-password}")
    private String keyStorePassword;

    @Bean
    RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(
                        keyStore.getURL(),
                        keyStorePassword.toCharArray()
                ).build();
        /* Option1: set up dns localhost,127.0.0.1 when generate cert
        * keytool -ext san=dns:localhost,ip:127.0.0.1
        * */
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);

        /* Option2: Since only our own certs are trusted, hostname verification is probably safe to bypass */
        /* SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new HostnameVerifier() {
                    @Override
                    public boolean verify(final String hostname,
                                          final SSLSession session) {
                        return true;
                    }
                });
         */

        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory).build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}

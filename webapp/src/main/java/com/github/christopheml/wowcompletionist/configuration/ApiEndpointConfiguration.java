package com.github.christopheml.wowcompletionist.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
@PropertySource({"classpath:configuration/config.properties", "classpath:configuration/credentials.properties"})
public class ApiEndpointConfiguration {

    @Value("${oauth2.accessTokenUri}")
    private String accessTokenUri;

    @Value("${oauth2.userAuthorizationUri}")
    private String userAuthorizationUri;

    @Value("${oauth2.clientId}")
    private String clientId;

    @Value("${oauth2.clientSecret}")
    private String clientSecret;

    @Value("${oauth2.tokenName}")
    private String tokenName;

    @Bean
    public OAuth2ProtectedResourceDetails blizzardRestApi() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId("blizzardRestApi");
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(accessTokenUri);
        details.setTokenName(tokenName);
        return details;
    }

    @Bean
    public OAuth2RestTemplate blizzardApiRestTemplate(OAuth2ClientContext clientContext) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(blizzardRestApi(), clientContext);
        restTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
        return restTemplate;
    }

}

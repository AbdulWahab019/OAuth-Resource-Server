package com.example.oauthresource.config;

import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings({"UnnecessaryLocalVariable", "unchecked", "rawtypes"})
public class CustomRemoteTokenService implements ResourceServerTokenServices {
    private final RestOperations restTemplate;
    private final AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    public CustomRemoteTokenService(){
        restTemplate = new RestTemplate();
        ( (RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            // Ignore 400
            @Override
            public void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {

                if(statusCode.value() != 400)
                    super.handleError(response, statusCode);
            }
        });
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        Map<String, Object> map = executeGet("http://localhost:8081/auth/oauth/check_token?token=" + accessToken);
        if (map == null || map.isEmpty() || map.get("error") != null) {
            throw new InvalidTokenException("Token not allowed");
        }
        return tokenConverter.extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        throw new UnsupportedOperationException("Not Supported: read access token.");
    }

    private Map<String, Object> executeGet(String path){
        try{
            Map map = restTemplate
                    .exchange(path,
                            HttpMethod.POST,
                            new HttpEntity<MultiValueMap<String, String>>(null, null),
                            Map.class)
                    .getBody();

            Map<String, Object> result = map;
            return result;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
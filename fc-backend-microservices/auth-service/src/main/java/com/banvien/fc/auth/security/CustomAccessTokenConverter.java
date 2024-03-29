package com.banvien.fc.auth.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomAccessTokenConverter extends JwtAccessTokenConverter {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetail) {
            CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
            Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
            info.put("userId", user.getUserId());
            info.put("outletId", user.getOutletId());
            info.put("countryId", user.getCountryId());
            customAccessToken.setAdditionalInformation(info);
        }
        return super.enhance(customAccessToken, authentication);
    }
}

package com.bridgeon.app.global.oauth2.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record KakaoUserResponseDto(
        Long id,
        @JsonProperty("kakao_account") Map<String, Object> kakaoAccount,
        Map<String, Object> properties
) {
    public String email() {
        if (kakaoAccount == null) {
            return null;
        }

        Object email = kakaoAccount.get("email");
        return email == null ? null : email.toString();
    }

    public String nickname() {
        if (kakaoAccount == null) {
            return null;
        }

        Object accountProfile = kakaoAccount.get("profile");
        if (accountProfile instanceof Map<?,?> map && map.get("nickname") != null) {
            return map.get("nickname").toString();
        }

        if (properties != null && properties.get("nickname") != null) {
            return properties.get("nickname").toString();
        }

        return "카카오사용자";
    }
}

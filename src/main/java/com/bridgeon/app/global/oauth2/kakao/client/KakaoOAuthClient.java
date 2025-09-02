package com.bridgeon.app.global.oauth2.kakao.client;

import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import com.bridgeon.app.global.oauth2.kakao.dto.KakaoTokenResponseDto;
import com.bridgeon.app.global.oauth2.kakao.dto.KakaoUserResponseDto;
import com.bridgeon.app.global.oauth2.kakao.properties.KakaoOAuthProperties;
import com.bridgeon.app.global.oauth2.kakao.properties.KakaoProviderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {

    private final KakaoOAuthProperties kakaoOAuthProperties;
    private final KakaoProviderProperties kakaoProviderProperties;
    private final RestTemplate restTemplate;

    public String exchangeCodeForAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("client_id", kakaoOAuthProperties.getClientId());
        form.add("redirect_uri", kakaoOAuthProperties.getRedirectUri()); // 인가요청과 완전 동일해야 함
        form.add("code", code);
        if (kakaoOAuthProperties.getClientSecret() != null && !kakaoOAuthProperties.getClientSecret().isBlank()) {
            form.add("client_secret", kakaoOAuthProperties.getClientSecret());
        }

        try {
            ResponseEntity<KakaoTokenResponseDto> resp =
                    restTemplate.postForEntity(kakaoProviderProperties.getTokenUri(), new HttpEntity<>(form, headers), KakaoTokenResponseDto.class);
            KakaoTokenResponseDto body = resp.getBody();
            if (resp.getStatusCode().is2xxSuccessful() && body != null && body.accessToken() != null) {
                return body.accessToken();
            }
            log.warn("Kakao token unexpected resp: status={}, body={}", resp.getStatusCode(), body);
            throw new BusinessException(AuthErrorCode.DENIED_KAKAO_TOKEN);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // ★ 여기서 원인을 바로 확인 가능
            log.warn("Kakao token error: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new BusinessException(AuthErrorCode.DENIED_KAKAO_TOKEN);
        } catch (ResourceAccessException e) {
            log.warn("Kakao token request failed (network/timeout): {}", e.getMessage());
            throw new BusinessException(AuthErrorCode.DENIED_KAKAO_TOKEN);
        }
    }

    public KakaoUserResponseDto fetchUser(String kakaoAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(kakaoAccessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<KakaoUserResponseDto> response = restTemplate.exchange(
                kakaoProviderProperties.getUserInfoUri(),
                HttpMethod.GET,
                request,
                KakaoUserResponseDto.class
        );
        log.info("KakaoUserResponseDto={}", response.getBody());

        KakaoUserResponseDto body = response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && body != null) {
            return body;
        }

        throw new BusinessException(AuthErrorCode.DENIED_KAKAO_USER);
    }
}

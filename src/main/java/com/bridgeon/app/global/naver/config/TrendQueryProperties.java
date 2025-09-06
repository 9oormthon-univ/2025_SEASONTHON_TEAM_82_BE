package com.bridgeon.app.global.naver.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "trend")
public class TrendQueryProperties {

    // 카테고리별 검색어 맵
    private Map<String, List<String>> queries;
}

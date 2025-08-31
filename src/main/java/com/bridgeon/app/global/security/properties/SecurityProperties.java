package com.bridgeon.app.global.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.path")
public class SecurityProperties {

    // Spring Security paths
    private List<String> PermitAll = new ArrayList<>();
}

package org.sopt.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public enum PublicEndpoint {

    AUTH_SIGNUP(HttpMethod.POST, "/api/v1/auth/signup"),
    AUTH_LOGIN(HttpMethod.POST, "/api/v1/auth/login"),
    AUTH_REISSUE(HttpMethod.POST, "/api/v1/auth/reissue"),
    POST_LIST(HttpMethod.GET, "/api/v1/posts"),
    POST_DETAIL(HttpMethod.GET, "/api/v1/posts/**"),
    SWAGGER_DOCS(null, "/v3/api-docs/**"),
    SWAGGER_UI(null, "/swagger-ui/**"),
    SWAGGER_HTML(null, "/swagger-ui.html");

    private final HttpMethod method;
    private final String pattern;
}

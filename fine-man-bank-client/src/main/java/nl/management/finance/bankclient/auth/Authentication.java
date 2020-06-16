package nl.management.finance.bankclient.auth;

import java.util.Locale;

public class Authentication {
    private final String accessToken;
    private final Long expiresAt;
    private final String refreshToken;
    private final String tokenType;

    public Authentication(String accessToken, Long expiresAt, String refreshToken, String tokenType) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "{accessToken=%s, expiresAt=%d, refreshToken=%s, tokenType=%s}",
                accessToken, expiresAt, refreshToken, tokenType);
    }
}

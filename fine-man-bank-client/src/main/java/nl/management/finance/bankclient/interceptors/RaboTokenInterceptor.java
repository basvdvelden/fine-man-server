package nl.management.finance.bankclient.interceptors;

import com.google.gson.Gson;
import nl.management.finance.bankclient.BCProperties;
import nl.management.finance.bankclient.IAppInterface;
import nl.management.finance.bankclient.auth.Authentication;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class RaboTokenInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RaboTokenInterceptor.class);
    private final BCProperties properties;
    private final IAppInterface context;

    private boolean refreshing = false;


    public RaboTokenInterceptor(BCProperties properties, @Lazy @Qualifier("external") IAppInterface context) {
        this.properties = properties;
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (!originalRequest.url().toString().contains("oauth")) {
            if (shouldRefreshAccessToken() && !refreshing) {
                refreshing = true;

                LOG.debug("bank token expired");
                Request tokenRequest = createRefreshRequest(originalRequest);
                Response response = chain.proceed(tokenRequest);
                if (!response.isSuccessful()) {
                    LOG.debug("refreshing bank token failed, error: {}", response.body().string());
                    response.close();
                    response = chain.proceed(createRefreshRequest(tokenRequest));
                    if (!response.isSuccessful()) {
                        LOG.warn("refreshing bank token failed twice, giving up. code: {}, error: {}",
                                response.code(), response.body().string());
                        response.close();
                        return chain.proceed(originalRequest);
                    }
                }
                RaboRefreshTokenResponse raboAuth = new Gson().fromJson(response.body().string(), RaboRefreshTokenResponse.class);
                context.updateBankAuth(new Authentication(raboAuth.getAccessToken(),
                        raboAuth.getExpiresIn() * 1000 + System.currentTimeMillis(), raboAuth.getRefreshToken(),
                        raboAuth.getTokenType()));
                refreshing = false;
            }
        }
        return chain.proceed(originalRequest);
    }

    private boolean shouldRefreshAccessToken() {
        return System.currentTimeMillis() + 5000 > context.getBankAuth().getExpiresAt();
    }

    private Request createRefreshRequest(Request originalRequest) {
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("refresh_token", context.getBankAuth().getRefreshToken())
                .build();
        return originalRequest
                .newBuilder()
                .post(body)
                .url(properties.getRaboApiUrl() + "oauth2/token")
                .addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(String.format("%s:%s",
                        properties.getRaboClientId(), properties.getRaboClientSecret()).getBytes()))
                .build();
    }
}

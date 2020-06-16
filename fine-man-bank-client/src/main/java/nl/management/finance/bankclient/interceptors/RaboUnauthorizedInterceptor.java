package nl.management.finance.bankclient.interceptors;

import nl.management.finance.bankclient.BankClientAuthorizationException;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RaboUnauthorizedInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RaboUnauthorizedInterceptor.class);

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.isSuccessful()) {
            LOG.debug("Bank response not successful, code: {}, response: {}", response.code(),
                    response.body() != null ? response.body().string() : "null");
            if (response.code() == 401) {
                throw new BankClientAuthorizationException();
            }
        }
        return response;
    }
}

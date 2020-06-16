package nl.management.finance.bankclient.config;

import nl.management.finance.bankclient.BCProperties;
import nl.management.finance.bankclient.api.RaboApi;
import nl.management.finance.bankclient.interceptors.RaboHeaderInterceptor;
import nl.management.finance.bankclient.interceptors.RaboTokenInterceptor;
import nl.management.finance.bankclient.interceptors.RaboUnauthorizedInterceptor;
import nl.management.finance.lib.SSLTrustManagerHelper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.InputStream;

@Configuration
public class RetrofitConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(RetrofitConfiguration.class);

    @Bean
    SSLTrustManagerHelper provideSSLTrustManagerHelper(BCProperties properties) {
        InputStream keystore = this.getClass().getClassLoader().getResourceAsStream(properties.getServerSSLKeyStore());
        InputStream truststore = this.getClass().getClassLoader().getResourceAsStream(properties.getServerSSLTrustStore());
        String keystorePwd = properties.getServerSslKeyStorePassword();
        String truststorePwd = properties.getServerSslTrustStorePassword();
        SSLTrustManagerHelper ssl = null;
        try {
            ssl = new SSLTrustManagerHelper(keystore, keystorePwd, truststore, truststorePwd);
        } catch (Exception e) {
            LOG.error("error occured while creating ssl trust manager helper!", e);
        }
        return ssl;
    }

    @Bean
    OkHttpClient provideHttpClient(SSLTrustManagerHelper sslTrustManagerHelper) {
        SSLContext context = null;
        try {
            context = sslTrustManagerHelper.clientSSLContext();
        } catch (Exception e) {
            LOG.error("Exception creating ssl context", e);
        }
        final SSLSocketFactory sslSocketFactory = context.getSocketFactory();
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        return okHttpClient.newBuilder()
                // .addInterceptor(logging)
                // TODO: Create hostname verifier.
                .sslSocketFactory(sslSocketFactory, sslTrustManagerHelper.getTrustManager())
                .hostnameVerifier((hostname, sslSession) -> true).build();
    }

    @Bean
    public RaboApi provideRaboApi(OkHttpClient httpClient, BCProperties properties, RaboTokenInterceptor raboTokenInterceptor,
                                  RaboHeaderInterceptor raboHeaderInterceptor, RaboUnauthorizedInterceptor raboUnauthorizedInterceptor) {
        OkHttpClient client = httpClient.newBuilder()
                .addInterceptor(raboTokenInterceptor)
                .addInterceptor(raboHeaderInterceptor)
                .addInterceptor(raboUnauthorizedInterceptor)
                .build();
        return new Retrofit.Builder()
                .baseUrl(properties.getRaboApiUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(RaboApi.class);
    }
}

package nl.management.finance;

import nl.management.finance.bankclient.BCProperties;
import nl.management.finance.bankclient.IAppInterface;
import nl.management.finance.bankclient.IBankAdapter;
import nl.management.finance.bankclient.RaboBankAdapter;
import nl.management.finance.bankclient.api.RaboApi;
import nl.management.finance.bankclient.interceptors.RaboHeaderInterceptor;
import nl.management.finance.bankclient.interceptors.RaboTokenInterceptor;
import nl.management.finance.bankclient.transaction.BCTransactionService;
import nl.management.finance.config.BankClientAppInterface;
import nl.management.finance.user.dao.UserBankRepository;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Component
public class BCServiceFactory {
    private final OkHttpClient client;
    private final BCProperties properties;
    private final UserBankRepository userBankRepository;
    private final RaboBankAdapter raboBankAdapter;

    public BCServiceFactory(OkHttpClient client, BCProperties properties, UserBankRepository userBankRepository, RaboBankAdapter raboBankAdapter) {
        this.client = client;
        this.properties = properties;
        this.userBankRepository = userBankRepository;
        this.raboBankAdapter = raboBankAdapter;
    }

    public BCTransactionService createTransactionService(final AuthContext authContext) {
        IAppInterface context = new BankClientAppInterface(authContext, userBankRepository, raboBankAdapter);
        return new BCTransactionService(context);
    }

    private RaboApi getRaboApi(AuthContext authContext) {
        RaboTokenInterceptor raboTokenInterceptor = new RaboTokenInterceptor(properties, getContextForInterceptor(authContext));
        RaboHeaderInterceptor raboHeaderInterceptor = new RaboHeaderInterceptor(properties, getContextForInterceptor(authContext));
        OkHttpClient client = this.client.newBuilder()
                .addInterceptor(raboTokenInterceptor)
                .addInterceptor(raboHeaderInterceptor)
                .build();
        return new Retrofit.Builder()
                .baseUrl(properties.getRaboApiUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(RaboApi.class);
    }

    private IAppInterface getContextForInterceptor(AuthContext authContext) {
        return new BankClientAppInterface(authContext, userBankRepository, null) {

            @Override
            public IBankAdapter getBankAdapter() {
                throw new IllegalStateException("This context was created for an interceptor which should not have to use this method.");
            }
        };
    }
}

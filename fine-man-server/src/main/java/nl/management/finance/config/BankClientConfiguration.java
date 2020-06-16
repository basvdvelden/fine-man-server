package nl.management.finance.config;

import nl.management.finance.AuthContext;
import nl.management.finance.bankclient.IAppInterface;
import nl.management.finance.bankclient.RaboBankAdapter;
import nl.management.finance.bankclient.config.RetrofitConfiguration;
import nl.management.finance.user.dao.UserBankRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@Import(RetrofitConfiguration.class)
public class BankClientConfiguration {

    @RequestScope
    @Bean(name = "external")
    IAppInterface provideDependantAppCallBacks(AuthContext context, UserBankRepository userBankRepository,
                                               RaboBankAdapter raboBankAdapter) {
        return new BankClientAppInterface(context, userBankRepository, raboBankAdapter);
    }
}

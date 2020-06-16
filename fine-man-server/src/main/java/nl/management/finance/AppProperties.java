package nl.management.finance;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String postPaymentRedirectUrl;

    public String getPostPaymentRedirectUrl() {
        return postPaymentRedirectUrl;
    }

    public void setPostPaymentRedirectUrl(String postPaymentRedirectUrl) {
        this.postPaymentRedirectUrl = postPaymentRedirectUrl;
    }
}

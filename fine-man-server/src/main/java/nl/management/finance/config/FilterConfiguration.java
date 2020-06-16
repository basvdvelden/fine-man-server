package nl.management.finance.config;

import nl.management.finance.filters.JWTFilter;
import nl.management.finance.filters.PinVerificationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class FilterConfiguration {
    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private PinVerificationFilter pinVerificationFilter;

    @Bean
    public FilterRegistrationBean<PinVerificationFilter> filterRegistrationBean() {
        FilterRegistrationBean<PinVerificationFilter> regBean = new FilterRegistrationBean<>();
        regBean.setFilter(pinVerificationFilter);
        regBean.setOrder(3);

        return regBean;
    }

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilterRegistrationBean() {
        FilterRegistrationBean<JWTFilter> regBean = new FilterRegistrationBean<>();
        regBean.setFilter(jwtFilter);
        regBean.setOrder(2);

        return regBean;
    }
}

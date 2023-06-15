package com.deyki.jobapplicationservice.config;

import com.deyki.jobapplicationservice.client.JobClient;
import com.deyki.jobapplicationservice.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Autowired
    public LoadBalancedExchangeFilterFunction loadBalanceFilter;

    @Bean
    public WebClient userWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://user-service")
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJpc3MiOiJJVEpvYnMtQVBJLXVzZXItc2VydmljZSIsImlhdCI6MTY4Njc0NTYwNiwidXNlcm5hbWUiOiJkZXlraSJ9.VyD_Dw-3MbERj3MLOx7t0a-93L7K0sM3AKvKQiMisFY"))
                .filter(loadBalanceFilter)
                .build();
    }

    @Bean
    public WebClient jobWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://job-service")
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJpc3MiOiJJVEpvYnMtQVBJLXVzZXItc2VydmljZSIsImlhdCI6MTY4Njc0NTYwNiwidXNlcm5hbWUiOiJkZXlraSJ9.VyD_Dw-3MbERj3MLOx7t0a-93L7K0sM3AKvKQiMisFY"))
                .filter(loadBalanceFilter)
                .build();
    }

    @Bean
    public UserClient userClient() {
        HttpServiceProxyFactory proxy = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(userWebClient()))
                .build();
        return proxy.createClient(UserClient.class);
    }

    @Bean
    public JobClient jobClient() {
        HttpServiceProxyFactory proxy = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(jobWebClient()))
                .build();
        return proxy.createClient(JobClient.class);
    }
}

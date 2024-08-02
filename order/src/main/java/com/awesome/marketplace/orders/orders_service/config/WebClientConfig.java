package com.awesome.marketplace.orders.orders_service.config;

import com.awesome.marketplace.orders.orders_service.clients.ProductClient;
import com.awesome.marketplace.orders.orders_service.exceptions.CustomClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient productsWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
//                .filter((request, next) -> next.exchange(request)
//                        .flatMap(response -> {
//                            if (response.statusCode().isError()) {
//                                return response.bodyToMono(String.class)
//                                        .flatMap(body -> Mono.error(new CustomClientException(response.statusCode(), body)));
//                            } else {
//                                return Mono.just(response);
//                            }
//                        })
//                )
                .build();
    }

    @Bean
    ProductClient productsClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(productsWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(ProductClient.class);
    }
}
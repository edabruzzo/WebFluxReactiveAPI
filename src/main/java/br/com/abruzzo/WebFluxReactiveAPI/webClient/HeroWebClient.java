package br.com.abruzzo.WebFluxReactiveAPI.webClient;

import br.com.abruzzo.WebFluxReactiveAPI.controller.HeroRestController;
import br.com.abruzzo.WebFluxReactiveAPI.model.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.abruzzo.WebFluxReactiveAPI.config.Endpoints.ENDPOINT_BASE;
import static br.com.abruzzo.WebFluxReactiveAPI.config.Endpoints.HEROES_ENDPOINT;

public class HeroWebClient {

    WebClient webClient = WebClient.create(ENDPOINT_BASE.getEndpoint());
    private static final Logger logger = LoggerFactory.getLogger(HeroWebClient.class);

    public void consume() {

        Mono<Hero> heroMono = webClient.get()
                .uri(HEROES_ENDPOINT.getEndpoint()+"/{id}", "1")
                .retrieve()
                .bodyToMono(Hero.class);

        heroMono.subscribe(System.out::println);
        logger.info("Consumindo Mono de id 1");



        Flux<Hero> heroFlux = webClient.get()
                .uri(HEROES_ENDPOINT.getEndpoint())
                .retrieve()
                .bodyToFlux(Hero.class);

        heroFlux.subscribe(System.out::println);
        logger.info("Consumindo Flux de heróis");
    }
}

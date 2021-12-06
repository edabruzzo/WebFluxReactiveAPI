package br.com.abruzzo.WebFluxReactiveAPI.webClient;

import br.com.abruzzo.WebFluxReactiveAPI.model.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.abruzzo.WebFluxReactiveAPI.config.ParametrosConfig.ENDPOINT_BASE;
import static br.com.abruzzo.WebFluxReactiveAPI.config.ParametrosConfig.HEROES_ENDPOINT;

public class HeroWebClient {

    WebClient webClient = WebClient.create(ENDPOINT_BASE.getValue());
    private static final Logger logger = LoggerFactory.getLogger(HeroWebClient.class);

    private HeroWebClient() {
        // Code smell -> java:S1118 -> Utility classes should not have public constructors
    }

    public static void main(String[] args){
        HeroWebClient heroWebClient = new HeroWebClient();
        heroWebClient.consume();
    }

    public void consume() {

        Mono<Hero> heroMono = webClient.get()
                .uri(HEROES_ENDPOINT.getValue()+"/{id}", "1")
                .retrieve()
                .bodyToMono(Hero.class);

        heroMono.subscribe(System.out::println);
        logger.info("Consumindo Mono de id 1");



        Flux<Hero> heroFlux = webClient.get()
                .uri(HEROES_ENDPOINT.getValue())
                .retrieve()
                .bodyToFlux(Hero.class);

        heroFlux.subscribe(System.out::println);
        logger.info("Consumindo Flux de heróis");
    }
}

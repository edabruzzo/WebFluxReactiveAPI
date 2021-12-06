package br.com.abruzzo.WebFluxReactiveAPI.service;

import br.com.abruzzo.WebFluxReactiveAPI.model.Hero;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IHeroService {

    Flux<Hero> findAll();
    Mono<Hero> findById(String id);
    Mono<Hero> save(Hero hero);
    void deleteById(String id);


}

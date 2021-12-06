package br.com.abruzzo.WebFluxReactiveAPI.service;

import br.com.abruzzo.WebFluxReactiveAPI.model.Hero;
import br.com.abruzzo.WebFluxReactiveAPI.repositories.HeroesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroService implements IHeroService {

    @Autowired
    private HeroesRepository heroesRepository;

    @Override public Flux<Hero> findAll() {return Flux.fromIterable(heroesRepository.findAll());}
    @Override public Mono<Hero> findById(String id) {return Mono.justOrEmpty(heroesRepository.findById(id));}
    @Override public Mono<Hero> save(Hero hero) {return Mono.justOrEmpty(heroesRepository.save(hero));}
    @Override public void deleteById(String id) {heroesRepository.deleteById(id);}

}

package br.com.abruzzo.WebFluxReactiveAPI.controller;

import br.com.abruzzo.WebFluxReactiveAPI.config.ParametrosConfig;
import br.com.abruzzo.WebFluxReactiveAPI.model.Hero;
import br.com.abruzzo.WebFluxReactiveAPI.service.HeroService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/heroes")
public class HeroRestController {

    private static final Logger logger = LoggerFactory.getLogger(HeroRestController.class);

    @Autowired
    private HeroService heroService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Mono<Hero>> getHeroById(@PathVariable String id) {
        logger.info("Chegou GET request no Endpoint {}/{}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                                                         , ParametrosConfig.HEROES_ENDPOINT.getValue()
                                                         ,id);
        Mono<Hero> hero = heroService.findById(id);
        HttpStatus status = (hero != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(hero,status);
    }

    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Flux<Hero> getAllHeroes(){
        logger.info("Chegou GET request no Endpoint {}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                                                      , ParametrosConfig.HEROES_ENDPOINT.getValue());
        return heroService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Hero> createHero(@RequestBody Hero hero){
        logger.info("POST request received on endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        Mono<Hero> heroSaved = null;
        try{
            heroSaved = heroService.save(hero);
        }catch(Exception erro){
            logger.debug(erro.getLocalizedMessage());
        }
        logger.info("Hero {} was saved",hero.getName());
        logger.info("{}", hero);
        return heroSaved;
    }


    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Hero>> updateHero(@PathVariable String id,
                                                    @RequestBody Hero heroUpdated) {
        heroUpdated.setId(id);
        Mono<ResponseEntity<Hero>> resposta = Mono.just(ResponseEntity.notFound().build());
        try {
            resposta = heroService.findById(id)
                    .flatMap(oldHero -> {
                        oldHero.setId(heroUpdated.getId());
                        oldHero.setUniverse(heroUpdated.getUniverse());
                        oldHero.setName(heroUpdated.getName());
                        oldHero.setCountFilms(heroUpdated.getCountFilms());
                        return heroService.save(oldHero);
                    }).map(hero -> ResponseEntity.ok(hero))
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        }catch(Exception erro){
                logger.debug(erro.getLocalizedMessage());
        }
        return resposta;

    }


    @DeleteMapping(value="{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public void delete(@PathVariable String id){
        logger.info("DELETE request received on endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        try{
            heroService.deleteById(id);
            logger.info("Hero with id {} was deleted", id);
        }catch (Exception erro){
            logger.debug(erro.getLocalizedMessage());
        }
    }


}

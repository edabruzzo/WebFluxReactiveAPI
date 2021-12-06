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
@RequestMapping("/heroes")
@Slf4j
public class HeroRestController {

    private static final Logger logger = LoggerFactory.getLogger(HeroRestController.class);

    @Autowired
    private HeroService heroService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Hero>> getHeroById(@PathVariable String id) {
        logger.info("Chegou GET request no Endpoint {}/{}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                                                         , ParametrosConfig.HEROES_ENDPOINT.getValue()
                                                         ,id);
        return heroService.findById(id).map((item)->new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/heroes", produces=MediaType.APPLICATION_JSON_VALUE)
    public Flux<Hero> getAllHeroes(){
        logger.info("Chegou GET request no Endpoint {}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                                                      , ParametrosConfig.HEROES_ENDPOINT.getValue());
        return heroService.findAll();
    }

    @PostMapping(value="/heroes",consumes = MediaType.APPLICATION_JSON_VALUE)
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


    @DeleteMapping(value="/heroes"+"{id}")
    @ResponseStatus(code=HttpStatus.CONTINUE)
    public Mono<HttpStatus> delete(@PathVariable String id){
        logger.info("DELETE request received on endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());

        try{
            heroService.deleteById(id);
            logger.info("Hero with id {} was deleted", id);
        }catch (Exception erro){
            logger.debug(erro.getLocalizedMessage());
            return Mono.just(HttpStatus.NOT_FOUND);
        }

        return Mono.just(HttpStatus.CONTINUE);

    }





}

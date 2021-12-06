package br.com.abruzzo.WebFluxReactiveAPI.controller;


import br.com.abruzzo.WebFluxReactiveAPI.config.ParametrosConfig;
import br.com.abruzzo.WebFluxReactiveAPI.model.Hero;
import br.com.abruzzo.WebFluxReactiveAPI.service.HeroService;
import com.amazonaws.services.dynamodbv2.document.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = HeroRestController.class)
@Import(HeroService.class)
class HeroRestControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @MockBean
    HeroService heroService;

    @Autowired
    WebTestClient webTestClient;

    private String URI_HEROES = ParametrosConfig.ENDPOINT_BASE.getValue()
                                    .concat(ParametrosConfig.HEROES_ENDPOINT.getValue());

    @Test
    void whenCallPOSTRequest_saveNewHeroOnDatabase(){

        Hero hero = new Hero();
        hero.setId("1");
        hero.setName("Homem Aranha");
        hero.setUniverse("XXX");
        hero.setCountFilms(3);

        Mockito.when(heroService.save(hero)).thenReturn(Mono.just(hero));
        webTestClient.post()
                .uri(URI_HEROES)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(hero))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(heroService, Mockito.times(1)).save(hero);


    }


    @Test
    void whenCallGETMethod_findAllHeroes() {

        //Hero(String id, String name, String universe, int count_films)
        Hero hero = new Hero("2", "Mulher Maravilha","dc comics", 2);
        Hero hero2 = new Hero("3", "Viuva negra", "marvel", 2);
        Hero hero3 = new Hero("4", "Capita marvel", "marvel", 2);

        Mockito.when(heroService.findAll()).thenReturn(Flux.just(hero, hero2, hero3));

        webTestClient.get()
                .uri(URI_HEROES)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Hero.class);

        Mockito.verify(heroService, Mockito.times(1)).findAll();
    }


    @Test
    void whenCallGETMethodById_findOneHero() {

        //Hero(String id, String name, String universe, int count_films)
        Hero hero = new Hero("2", "Mulher Maravilha","dc comics", 2);

        Mockito.when(heroService.findById(hero.getId()).thenReturn(Mono.just(hero)));

        webTestClient.get()
                .uri(URI_HEROES + "/{}",hero.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Hero.class);

        Mockito.verify(heroService, Mockito.times(1)).findById(hero.getId());

    }




}

package br.com.abruzzo.WebFluxReactiveAPI.controller;


import br.com.abruzzo.WebFluxReactiveAPI.config.ParametrosConfig;
import br.com.abruzzo.WebFluxReactiveAPI.model.Hero;
import br.com.abruzzo.WebFluxReactiveAPI.repositories.HeroesRepository;
import br.com.abruzzo.WebFluxReactiveAPI.service.HeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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


    private Logger logger = LoggerFactory.getLogger(HeroRestControllerTest.class);

    private Hero hero1;
    private Hero hero2;
    private Hero hero3;
    private Hero hero4;


    @BeforeEach
    public void setUp() {
        System.out.println("@Beforeeach is called!");
        MockitoAnnotations.initMocks(this);

        hero1 = new Hero("1", "Mulher Maravilha","dc comics", 2);
        hero2 = new Hero("2", "Viuva negra", "marvel", 2);
        hero3 = new Hero("3", "Capita marvel", "marvel", 2);
        hero4 = new Hero("4", "Tartaruga Ninja", "dc comics", 4);

    }


    @Test
    void whenCallPOSTRequest_saveNewHeroOnDatabase(){

        Mockito.when(heroService.save(hero1)).thenReturn(Mono.just(hero1));
        webTestClient.post()
                .uri(URI_HEROES)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(hero1))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(heroService, Mockito.times(1)).save(hero1);


    }


    @Test
    void whenCallGETMethod_findAllHeroes() {

        Mockito.when(heroService.findAll()).thenReturn(Flux.just(hero1, hero2, hero3,hero4));

        webTestClient.get()
                .uri(URI_HEROES)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Hero.class);

        Mockito.verify(heroService, Mockito.times(1)).findAll();
    }


    @Test
    void whenCallGETMethodById_findOneHero() {

        Mockito.when(heroService.findById(hero2.getId())
                .thenReturn(Mono.just(hero2)));

        webTestClient.get()
                .uri("/{id}",hero2.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo("2")
                .jsonPath("$.name").isEqualTo("Viuva negra")
                .jsonPath("$.universe").isEqualTo("marvel")
                .jsonPath("$.countFilms").isEqualTo(2);

        Mockito.verify(heroService, Mockito.times(1)).findById(hero2.getId());

    }


    @Test
    void testDeleteHeroe(){

        webTestClient.get().uri("/delete/{id}", "1")
                .exchange()
                .expectStatus().isNotFound();
    }




}

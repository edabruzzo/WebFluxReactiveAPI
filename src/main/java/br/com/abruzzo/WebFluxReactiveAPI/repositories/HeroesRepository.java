package br.com.abruzzo.WebFluxReactiveAPI.repositories;

import br.com.abruzzo.WebFluxReactiveAPI.model.Hero;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


@EnableScan
public interface HeroesRepository extends CrudRepository<Hero, String> { }
package br.com.abruzzo.WebFluxReactiveAPI;

import br.com.abruzzo.WebFluxReactiveAPI.config.HeroPopulateData;
import br.com.abruzzo.WebFluxReactiveAPI.config.HeroesTableConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebFluxReactiveApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxReactiveApiApplication.class, args);
		try {
			HeroesTableConfig.criaSchemaTabelas();
			HeroPopulateData.popularTabela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

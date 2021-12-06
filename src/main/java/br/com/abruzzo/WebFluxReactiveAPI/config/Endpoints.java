package br.com.abruzzo.WebFluxReactiveAPI.config;

public enum Endpoints {

    ENDPOINT_BASE("http://localhost:8080"),
    HEROES_ENDPOINT("/heroes");

    private final String endpoint;

    Endpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }
}

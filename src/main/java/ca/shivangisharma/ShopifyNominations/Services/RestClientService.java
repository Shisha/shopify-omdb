package ca.shivangisharma.ShopifyNominations.Services;

import ca.shivangisharma.ShopifyNominations.Entity.Movies;
import ca.shivangisharma.ShopifyNominations.Entity.Search;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;


import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
@Service
public class RestClientService implements Serializable {

    @Value("${server.port}")
    private String serverPort;

    private static final String baseURIwithTitle = "http://www.omdbapi.com/?i=tt3896198&apikey=23c394e1&s=";

    public List<Search> getMovies(String title) {
        String titleQueryParam = prepareQuery(title);
        RequestHeadersSpec<?> spec = WebClient.create().get().uri(titleQueryParam);
        List<Search> searches = spec.retrieve().toEntity(Movies.class).block().getBody().getSearch()
                .stream()
                .filter( s -> s.getType().equals("movie"))
                .collect(Collectors.toList());

        return searches;
    }

    private String prepareQuery(String title) {
        return baseURIwithTitle + title.replace(' ', '+') ;
    }


}

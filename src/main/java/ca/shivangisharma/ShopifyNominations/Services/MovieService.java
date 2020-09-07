package ca.shivangisharma.ShopifyNominations.Services;

import ca.shivangisharma.ShopifyNominations.Entity.Search;
import ca.shivangisharma.ShopifyNominations.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public MovieService(@Autowired MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void nominate(Search searchItem) {
        if(movieRepository.count() < 5)
            movieRepository.save(searchItem);
    }

    public void removeNominations(Search item) {
        movieRepository.delete(item);
    }

    public long getTotalNominations() {
        return movieRepository.count();
    }

    public List<Search> getListOfNominations() {
        return movieRepository.findAll();
    }
}

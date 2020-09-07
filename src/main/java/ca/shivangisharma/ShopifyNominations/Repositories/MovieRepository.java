package ca.shivangisharma.ShopifyNominations.Repositories;

import ca.shivangisharma.ShopifyNominations.Entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Search, Long> {

}

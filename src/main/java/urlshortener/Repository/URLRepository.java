package urlshortener.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.Model.URL;

public interface URLRepository extends JpaRepository<URL, Long> {
}

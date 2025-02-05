package ma.library.livreservice.repositories;

import ma.library.livreservice.entities.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepository extends JpaRepository<Livre, Long> {
}

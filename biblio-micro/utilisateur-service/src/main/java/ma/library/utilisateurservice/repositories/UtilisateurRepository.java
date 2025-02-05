package ma.library.utilisateurservice.repositories;

import ma.library.utilisateurservice.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}

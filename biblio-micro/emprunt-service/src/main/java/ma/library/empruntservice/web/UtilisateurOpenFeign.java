package ma.library.empruntservice.web;

import ma.library.empruntservice.module.Utilisateur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Service
@FeignClient(name="utilisateur-service")
public interface UtilisateurOpenFeign {

        @GetMapping("/utilisateurs")
        public List<Utilisateur> findAll();

        @GetMapping("/utilisateurs/{id}")
        public Utilisateur findById(@PathVariable Long id);

}

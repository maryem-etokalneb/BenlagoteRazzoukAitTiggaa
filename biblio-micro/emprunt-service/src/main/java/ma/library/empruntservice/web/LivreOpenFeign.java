package ma.library.empruntservice.web;

import ma.library.empruntservice.module.Livre;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Service
@FeignClient(name="livre-service")
public interface LivreOpenFeign {

        @GetMapping("/livres")
        public List<Livre> findAll();

        @GetMapping("/livres/{id}")
        public Livre findById(@PathVariable Long id);
    }


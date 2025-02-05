package ma.library.livreservice.web;

import ma.library.livreservice.entities.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ma.library.livreservice.repositories.LivreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livres")
@CrossOrigin(origins = "http://localhost:4200")
public class LivreController {

    private final LivreRepository livreRepository;

    @Autowired
    public LivreController(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    // Ajouter un livre
    @PostMapping
    public Livre ajouterLivre(@RequestBody Livre livre) {
        System.out.println("Received book: " + livre);
        return livreRepository.save(livre);
    }

    @GetMapping
    public List<Livre> allLivres() {
        return livreRepository.findAll();
    }

    @GetMapping("/{id}")
    public Livre aLivre(@PathVariable Long id) {
        return livreRepository.findById(id).orElse(null); // Handle case where book is not found
    }

    @DeleteMapping("/{id}")
    public void deletelivre(@PathVariable Long id) {
        livreRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Livre updateLivre(@RequestBody Livre livre, @PathVariable Long id) {
        livre.setIdLiv(id);
        return livreRepository.save(livre);
    }

    @GetMapping("/search")
    public List<Livre> searchLivres(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre) {


        List<Livre> livres = livreRepository.findAll();


        if (title != null && !title.isEmpty()) {
            livres = livres.stream()
                    .filter(l -> l.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (author != null && !author.isEmpty()) {
            livres = livres.stream()
                    .filter(l -> l.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (genre != null && !genre.isEmpty()) {
            livres = livres.stream()
                    .filter(l -> l.getGenre().toLowerCase().contains(genre.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (livres.isEmpty()) {
            System.out.println("Aucun livre trouvé");
            throw new RuntimeException("Aucun livre trouvé pour les critères spécifiés.");
        }
        return livres;
    }


}

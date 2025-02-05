package ma.library.utilisateurservice.web;


import ma.library.utilisateurservice.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ma.library.utilisateurservice.repositories.UtilisateurRepository;

import java.time.Clock;
import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    @PostMapping("/login")
    public Utilisateur loginUser(@RequestBody Utilisateur utilisateur) {
        List<Utilisateur> users = utilisateurRepository.findAll();
        for (Utilisateur existingUser : users) {
            if (existingUser.getEmail().equalsIgnoreCase(utilisateur.getEmail())) {
                if (existingUser.getPassword().equals(utilisateur.getPassword())) {
                    System.out.println("connected");
                    return existingUser;
                } else {
                    throw new RuntimeException("Erreur : Mot de passe incorrect.");
                }
            }
        }
        throw new RuntimeException("Erreur : L'email n'est pas trouvé.");
    }

    @PostMapping
    public Utilisateur registerUser(@RequestBody Utilisateur utilisateur) {
        List<Utilisateur> allUti = utilisateurRepository.findAll();


        for (Utilisateur existingUser : allUti) {
            if (existingUser.getEmail().equalsIgnoreCase(utilisateur.getEmail())) {
                throw new RuntimeException("Erreur : L'email est déjà utilisé. Veuillez utiliser un autre email.");
            }
        }

        Utilisateur allUtis = utilisateurRepository.save(utilisateur);
        return allUtis;
    }

    @PutMapping("/{id}")
    public Utilisateur updateUser(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        utilisateur.setIdUti(id);
        return utilisateurRepository.save(utilisateur);
    }
    @GetMapping
    public List<Utilisateur> allUtilisateur() {
        return utilisateurRepository.findAll();
    }

    @GetMapping("/{id}")
    public Utilisateur getUser(@PathVariable Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        utilisateurRepository.deleteById(id);
    }



}

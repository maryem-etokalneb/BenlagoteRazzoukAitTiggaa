package ma.library.empruntservice.web;



import jakarta.transaction.Transactional;
import ma.library.empruntservice.entities.Emprunt;
import ma.library.empruntservice.module.Livre;
import ma.library.empruntservice.module.Utilisateur;
import ma.library.empruntservice.repositories.EmpruntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/emprunt")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpruntController {
    @Autowired
    private final EmpruntRepository empruntRepository;
    @Autowired
    private final UtilisateurOpenFeign utilisateurOpenFeign;
    @Autowired
    private final LivreOpenFeign livreOpenFeign;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public EmpruntController(EmpruntRepository empruntRepository,
                             UtilisateurOpenFeign utilisateurOpenFeign,
                             LivreOpenFeign livreOpenFeign) {
        this.empruntRepository = empruntRepository;
        this.utilisateurOpenFeign = utilisateurOpenFeign;
        this.livreOpenFeign = livreOpenFeign;
    }
    @GetMapping("/emprunts")
    public List<Emprunt> getEmprunts() {
        List<Emprunt> emprunts = empruntRepository.findAll();
        System.out.println("Afficher emprunts: " + emprunts);

        List<Utilisateur> utilisateurs = utilisateurOpenFeign.findAll();
        System.out.println("Fetched utilisateurs: " + utilisateurs);

        List<Livre> livres = livreOpenFeign.findAll();
        System.out.println("Afficher livres: " + livres);

        for (Emprunt emprunt : emprunts) {
            utilisateurs.stream()
                    .filter(u -> u.getIdUti().equals(emprunt.getUtilisateurId()))
                    .findFirst()
                    .ifPresent(emprunt::setUtilisateur);

            livres.stream()
                    .filter(l -> l.getIdLiv().equals(emprunt.getLivreId()))
                    .findFirst()
                    .ifPresent(emprunt::setLivre);
        }

        return emprunts;
    }

    @PostMapping("/emprunt")
    public Emprunt ajouterEmprunt(@RequestBody Emprunt empruntRequest) {
        // Vérifier que l'utilisateur existe
        Utilisateur utilisateur = utilisateurOpenFeign.findById(empruntRequest.getUtilisateurId());
        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID: " + empruntRequest.getUtilisateurId());
        }

        // Vérifier que le livre existe
        Livre livre = livreOpenFeign.findById(empruntRequest.getLivreId());
        if (livre == null) {
            throw new RuntimeException("Livre non trouvé avec l'ID: " + empruntRequest.getLivreId());
        }

        return empruntRepository.save(empruntRequest);
    }




    @Transactional
    @Scheduled(cron = "* * 0 * * *") //pour modifier les penalities automatiquement
    public void ModifierPenalites() {
        System.out.println("Modifier  penalties");

        List<Emprunt> emprunts = empruntRepository.findAll();
        LocalDate aujourdHui = LocalDate.now();

        for (Emprunt emprunt : emprunts) {
            System.out.println(" le traitement est sur ID : " + emprunt.getId());

            if (emprunt.getDateRetourReelle() != null) {
                emprunt.setPenalite(0.0);
                System.out.println("pas de penality pour ID: " + emprunt.getId() + " (déjà retourné)");
                continue;
            }

            if (emprunt.getDateRetourPrevue() != null && emprunt.getDateRetourPrevue().isBefore(aujourdHui)) {

                long joursRetard = aujourdHui.toEpochDay() - emprunt.getDateRetourPrevue().toEpochDay();

                double penalite = joursRetard * 2.0;

                double updatedPenalite = emprunt.getPenalite() + penalite;
                emprunt.setPenalite(updatedPenalite);
                System.out.println("modifier penality pour  Emprunt ID: " + emprunt.getId() + " -> " + updatedPenalite);
            } else {
                emprunt.setPenalite(0.0);
                System.out.println("pas de penality pour  Emprunt ID: " + emprunt.getId());
            }
        }
        System.out.println("enregestrer les modifications dans la  base de données ...");
        empruntRepository.saveAll(emprunts);
        empruntRepository.flush();
        System.out.println("Penalties bien modifier pour tous les  emprunts.");
    }

    @DeleteMapping("/emprunts/{id}")
    public String deleteEmprunt(@PathVariable Long id) {
        // verification de l'existance Emprunt
        if (empruntRepository.existsById(id)) {
            empruntRepository.deleteById(id);
            return "Emprunt est supprimé avec succées ";
        } else {
            return "Emprunt  n'existe pas .";
        }
    }
    @PutMapping("/emprunts/{id}")
    public String updateEmprunt(@PathVariable Long id, @RequestBody Emprunt updatedEmprunt) {
        // Vérifier si l'emprunt avec l'id donné existe
        if (empruntRepository.existsById(id)) {
            // Récupérer l'emprunt existant
            Emprunt emprunt = empruntRepository.findById(id).get();
            // Mettre à jour uniquement les éléments fournis par l'utilisateur
            if (updatedEmprunt.getUtilisateurId() != null) {
                emprunt.setUtilisateurId(updatedEmprunt.getUtilisateurId());
            }
            if (updatedEmprunt.getLivreId() != null) {
                emprunt.setLivreId(updatedEmprunt.getLivreId());
            }
            if (updatedEmprunt.getDateEmprunt() != null) {
                emprunt.setDateEmprunt(updatedEmprunt.getDateEmprunt());
            }
            if (updatedEmprunt.getDateRetourPrevue() != null) {
                emprunt.setDateRetourPrevue(updatedEmprunt.getDateRetourPrevue());
            }
            if (updatedEmprunt.getDateRetourReelle() != null) {
                emprunt.setDateRetourReelle(updatedEmprunt.getDateRetourReelle());
            }
            if (updatedEmprunt.isRetourne() != emprunt.isRetourne()) {
                emprunt.setRetourne(updatedEmprunt.isRetourne());
            }

            if (updatedEmprunt.getPenalite() != 0.0) {
                emprunt.setPenalite(updatedEmprunt.getPenalite());
            }
            empruntRepository.save(emprunt);

            return "Emprunt mis à jour avec succès.";
        } else {
            return "Emprunt non trouvé.";
        }
    }

    @GetMapping("/emprunts/{userId}")
    public List<Emprunt> getUtilisEmprunts(@PathVariable Long userId) {
        List<Emprunt> emprunts = empruntRepository.findAll();

        List<Utilisateur> utilisateurs = utilisateurOpenFeign.findAll();
        List<Livre> livres = livreOpenFeign.findAll();

        // Filter emprunts by the user ID
        emprunts = emprunts.stream()
                .filter(emprunt -> emprunt.getUtilisateurId().equals(userId))
                .toList();

        // Set user and book details
        for (Emprunt emprunt : emprunts) {
            utilisateurs.stream()
                    .filter(u -> u.getIdUti().equals(emprunt.getUtilisateurId()))
                    .findFirst()
                    .ifPresent(emprunt::setUtilisateur);

            livres.stream()
                    .filter(l -> l.getIdLiv().equals(emprunt.getLivreId()))
                    .findFirst()
                    .ifPresent(emprunt::setLivre);
        }

        return emprunts;
    }





    @Scheduled(cron = "0 26 15 * * ?")
    @Transactional
    public void sendEmailReminders() {
        System.out.println("envoyer une email à utilisateur ");

        List<Emprunt> emprunts = empruntRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Emprunt emprunt : emprunts) {
            if (emprunt.getDateRetourPrevue() != null
                    && emprunt.getDateRetourReelle() == null
                    && emprunt.getDateRetourPrevue().minusDays(1).isEqual(today)) {

                // Fetch user details
                Utilisateur utilisateur = utilisateurOpenFeign.findById(emprunt.getUtilisateurId());
                Livre livre=livreOpenFeign.findById(emprunt.getLivreId());
                if (utilisateur != null && utilisateur.getEmail() != null) {
                    // Send email reminder
                    sendEmail(utilisateur.getEmail(),
                            "Rappel de Retour de Livre",
                            "Cher " + utilisateur.getName() + ",\n\n" +
                                    "Ceci est un rappel de retourner le livre '" + livre.getTitle() + "' d'ici demain (" + emprunt.getDateRetourPrevue() + ").\n\n" +
                                    "Merci d'utiliser notre service de bibliothèque.\n\nCordialement");
                }
            }
        }
    }

    // Helper method to send an email
    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try {
            mailSender.send(message);
            System.out.println("Email sent to: " + to);
        } catch (Exception e) {
            System.err.println("Failed to send email to: " + to + " - " + e.getMessage());
        }
    }






}

package ma.library.empruntservice;

import ma.library.empruntservice.entities.Emprunt;
import ma.library.empruntservice.repositories.EmpruntRepository;
import ma.library.empruntservice.web.LivreOpenFeign;
import ma.library.empruntservice.web.UtilisateurOpenFeign;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "ma.library.empruntservice.web")
public class EmpruntServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmpruntServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(EmpruntRepository empruntRepository) {
        return args -> {
            // Create Emprunt entities and set relationships with Livre and Utilisateur
            Emprunt emprunt1 = Emprunt.builder()
                    .utilisateurId(1L)
                    .livreId(1L)
                    .dateEmprunt(LocalDate.of(2025, 1, 1))
                    .dateRetourPrevue(LocalDate.of(2025, 1, 21))
                    .retourne(false)

                    .build();
            empruntRepository.save(emprunt1);

            Emprunt emprunt2 = Emprunt.builder()
                    .utilisateurId(1L)
                    .livreId(2L)
                    .dateEmprunt(LocalDate.of(2025, 1, 3))
                    .dateRetourPrevue(LocalDate.of(2025, 1, 20))
                    .retourne(false)

                    .build();
            empruntRepository.save(emprunt2);

            Emprunt emprunt3 = Emprunt.builder()
                    .utilisateurId(2L)
                    .livreId(3L)
                    .dateEmprunt(LocalDate.now().minusDays(10))
                    .dateRetourPrevue(LocalDate.now().minusDays(1)) // Date passée
                    .retourne(false)
                    .build();
            empruntRepository.save(emprunt3);

            System.out.println("Initial Emprunts added to the database.");
        };
    }

    @Bean
    CommandLineRunner testFeign(LivreOpenFeign livreOpenFeign, UtilisateurOpenFeign utilisateurOpenFeign) {
        return args -> {
            System.out.println("Fetching all livres:");
            try {
                System.out.println(livreOpenFeign.findAll());
            } catch (Exception e) {
                System.err.println("Error fetching livres: " + e.getMessage());
            }

            System.out.println("Fetching all utilisateurs:");
            try {
                System.out.println(utilisateurOpenFeign.findAll());
            } catch (Exception e) {
                System.err.println("Error fetching utilisateurs: " + e.getMessage());
            }
        };
    }
}

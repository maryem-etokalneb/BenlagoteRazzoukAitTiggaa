package ma.library.utilisateurservice;

import ma.library.utilisateurservice.entities.Utilisateur;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ma.library.utilisateurservice.repositories.UtilisateurRepository;

@SpringBootApplication

@EntityScan(basePackages = "ma/library/utilisateurservice/entities")
public class UtilisateurServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilisateurServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(UtilisateurRepository utilisateurRepository) {
        return args -> {
            Utilisateur user1 = Utilisateur.builder()
                    .name("maryem")
                    .email("maryem.benlagot@gmail.com")
                    .password("123456789")
                    .address("rabat")
                    .build();

            utilisateurRepository.save(user1);

            Utilisateur user2 = Utilisateur.builder()
                    .name("laila")
                    .email("lailatigga.96@gmail.com")
                    .password("123456789")
                    .address("kenitra")
                    .build();

            utilisateurRepository.save(user2);

            Utilisateur user3 = Utilisateur.builder()
                    .name("oumaima")
                    .email("oumaima.razzouk@um5r.ac.ma")
                    .password("123456789")
                    .address("salé")
                    .build();

            utilisateurRepository.save(user3);

            System.out.println("les utilisateurs sont enregestrés dans la base de donnée");
        };
    }
}

package ma.library.livreservice;

import ma.library.livreservice.entities.Livre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ma.library.livreservice.repositories.LivreRepository;

@SpringBootApplication

@EntityScan(basePackages = "ma/library/livreservice/entities")
public class LivreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LivreServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(LivreRepository LivreRepository) {
        return args -> {

            Livre liv = Livre.builder()
                    .title("Java Programming")
                    .author("salim")
                    .genre("Technology")
                    .build();

            LivreRepository.save(liv);
            Livre liv2 = Livre.builder()
                    .title("Java ")
                    .author("fadil")
                    .genre("Tech")
                    .build();

            LivreRepository.save(liv2);
            Livre liv3 = Livre.builder()
                    .title("go Programming")
                    .author("hamid")
                    .genre("Game")
                    .build();

            LivreRepository.save(liv3);

        };
    }
}

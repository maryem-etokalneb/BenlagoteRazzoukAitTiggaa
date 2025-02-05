package ma.library.empruntservice.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.library.empruntservice.module.Livre;
import ma.library.empruntservice.module.Utilisateur;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long utilisateurId;
    private Long livreId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourReelle;
    @Builder.Default
    private Double penalite =0.0;
    @Builder.Default
    private boolean retourne =false;

    @Transient
    private Utilisateur utilisateur;
    @Transient
    private Livre livre;
}

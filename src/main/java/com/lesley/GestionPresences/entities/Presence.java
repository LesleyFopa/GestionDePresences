package com.lesley.GestionPresences.entities;

import com.lesley.GestionPresences.Enum.StatusPresence;
import com.lesley.GestionPresences.Enum.TypeScan;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name ="Presences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull(message = "La date et heure sont obligatoires")
    @PastOrPresent(message = "La date et heure ne peuvent pas être dans le futur")
    private LocalDateTime dateHeure;

    @NotNull(message = "Le statut de présence est obligatoire")
    @Enumerated(EnumType.STRING)
    private StatusPresence statusPresence;

    @NotNull(message = "Le type de scan est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypeScan typeScan;

    @Min(value = 0, message = "Le retard ne peut pas être négatif")
    @Max(value = 240, message = "Le retard ne peut pas dépasser 240 minutes")
    private Integer retardMinute;

    private String tokenUtilise ;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;

    // Validations de cohérence métier
    @AssertTrue(message = "Une présence ne peut pas avoir de retard si le statut est ABSENT")
    private boolean isRetardCoherent() {
        return !StatusPresence.ABSENT.equals(statusPresence) || retardMinute == 0;
    }

}

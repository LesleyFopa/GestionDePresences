package com.lesley.GestionPresences.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du cours est obligatoire")
    @Size(min = 2, max = 200, message = "Le nom doit contenir entre 2 et 200 caractères")
    private String nom;

    @NotNull(message = "L'heure de début est obligatoire")
    @Future(message = "L'heure de début doit être dans le futur pour la planification")
    private LocalDateTime heureDebut;

    @NotNull(message = "L'heure de fin est obligatoire")
    @Future(message = "L'heure de fin doit être dans le futur pour la planification")
    private LocalDateTime heureFin;

    @NotBlank(message = "La salle est obligatoire")
    @Size(max = 50, message = "La salle ne peut pas dépasser 50 caractères")
    private String  salle;

    @NotNull(message = "La plage de validation est obligatoire")
    @Min(value = 5, message = "La plage de validation doit être d'au moins 5 minutes")
    @Max(value = 60, message = "La plage de validation ne peut pas dépasser 60 minutes")
    private Integer plageValidation;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private List<Presence> presences = new ArrayList<>();

    // Validation de cohérence des dates
    @AssertTrue(message = "L'heure de fin doit être après l'heure de début")
    private boolean isHeuresCoherentes() {
        return heureDebut != null && heureFin != null && heureFin.isAfter(heureDebut);
    }

    // Validation de durée raisonnable
    @AssertTrue(message = "La durée du cours ne peut pas dépasser 6 heures")
    private boolean isDureeRaisonnable() {
        if (heureDebut == null || heureFin == null) return true;
        return Duration.between(heureDebut, heureFin).toHours() <= 6;
    }
}

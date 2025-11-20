package com.lesley.GestionPresences.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le token de session est obligatoire")
    @Size(min = 64, message = "Le token de session doit contenir au moins 64 caractères")
    private String token;

    @NotNull(message = "La date de connexion est obligatoire")
    @PastOrPresent(message = "La date de connexion ne peut pas être dans le futur")
    private LocalDateTime dateConnexion;

    @NotNull(message = "La date d'expiration est obligatoire")
    @Future(message = "La date d'expiration doit être dans le futur")
    private LocalDateTime dateExpiration;

    @Size(max = 100, message = "L'appareil ne peut pas dépasser 100 caractères")
    private String appareil;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @AssertTrue(message = "La session doit expirer après la date de connexion")
    private boolean isExpirationValide() {
        return dateConnexion != null && dateExpiration != null && dateExpiration.isAfter(dateConnexion);
    }
}

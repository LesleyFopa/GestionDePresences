package com.lesley.GestionPresences.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name="Etudiants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etudiant extends User {
    @NotNull(message = "Le matricule ne peut pas être nul")
    @Size(min = 3, max = 20, message = "Le matricule doit contenir entre 3 et 20 caractères")
    private String matricule;

    @NotNull(message = "La classe ne peut pas être nul")
    @Size(max = 20, message = "La classe ne peut pas dépasser 20 caractères")
    private String classe;

    @Size(max = 20, message = "Le groupe ne peut pas dépasser 20 caractères")
    private String groupe;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Numéro de téléphone invalide")
    private String telephone;

    private boolean qrCodeActif;

}

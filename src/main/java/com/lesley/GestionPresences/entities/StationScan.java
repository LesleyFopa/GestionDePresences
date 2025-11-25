package com.lesley.GestionPresences.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StationScan extends User {

    @NotBlank(message = "La localisation est obligatoire")
    @Size(max = 100, message = "La localisation ne peut pas dépasser 100 caractères")
    private String location;

    @NotBlank(message = "L'adresse MAC est obligatoire")
    @Pattern(
            regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$",
            message = "L'adresse MAC doit être au format valide"
    )
    private String macAddress;

    @NotNull(message = "Le statut en ligne est obligatoire")
    private boolean enLigne;

    @PastOrPresent(message = "La dernière synchronisation ne peut pas être dans le futur")
    private LocalDateTime derniereSync;

    @NotNull(message = "Le statut actif est obligatoire")
    private boolean actif;

    @ManyToOne
    @JoinColumn(name = "presence_id")
    private Presence presence;

    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    private  Administrateur administrateur;
}

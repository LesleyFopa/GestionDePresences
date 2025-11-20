package com.lesley.GestionPresences.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull(message = "Le nom ne peut pas être nul")
    @Size(min = 2, max = 200, message = "Le nom doit contenir entre 2 et 200 caractères")
    private String nom;

    @NotNull(message = "L'email ne peut pas être nul")
    @Email(message = "L'email doit être une adresse email valide")
    private String email;


    @NotNull(message = "Le mot de passe ne peut pas être nul")
    @Size(min = 4, message = "Le mot de passe doit contenir au moins 4 caractères")
    private String motDePasse;

    private LocalDateTime dateCreation;

    private boolean active;

}

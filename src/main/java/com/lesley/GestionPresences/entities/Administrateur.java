package com.lesley.GestionPresences.entities;


import com.lesley.GestionPresences.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrateur extends User {

    @ElementCollection // ← SOLUTION : Annotation ajoutée ici
    @CollectionTable(
            name = "admin_permissions", // Table de jointure pour les permissions
            joinColumns = @JoinColumn(name = "admin_id")
    )
    @Column(name = "permission", length = 50) // Colonne dans la table de jointure
    @Size(max = 50, message = "Chaque permission ne peut pas dépasser 50 caractères")
    private List<String> permissions;
}

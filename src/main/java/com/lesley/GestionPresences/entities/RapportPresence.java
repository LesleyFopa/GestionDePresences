package com.lesley.GestionPresences.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RapportPresence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de début de période est obligatoire")
    @Future(message = "La date de début de période doit être dans le futur")
    private LocalDateTime periodeDebut;

    @NotNull(message = "La date de fin de période est obligatoire")
    @Future(message = "La date de fin de période doit être dans le futur")
    private LocalDateTime periodeFin;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rapport_presence_id")
    private List<StatistiquePresence> donnees;


    @ElementCollection
    @CollectionTable(
            name = "rapport_filtres",
            joinColumns = @JoinColumn(name = "rapport_id")
    )
    @MapKeyColumn(name = "filtre_cle")
    @Column(name = "filtre_valeur")
    private Map<String,String> filtres;


    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    private Administrateur administrateur;
}

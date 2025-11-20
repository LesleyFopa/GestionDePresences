package com.lesley.GestionPresences.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatistiquePresence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classe;

    private Integer totalPresences;

    private Integer totalAbsences;

    private Double tauxPresences;

    private Double moyenneRetard;

    @ManyToOne
    @JoinColumn(name = "rapport_presence_id")
    private RapportPresence rapportPresence;
}

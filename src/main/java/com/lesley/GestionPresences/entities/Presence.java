package com.lesley.GestionPresences.entities;

import com.lesley.GestionPresences.Enum.StatusPresence;
import com.lesley.GestionPresences.Enum.TypeScan;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name ="Presences")
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private LocalDate dateHeure;
    private StatusPresence statusPresence;
    private TypeScan typeScan;
    private Integer retardMinute;

}

package com.lesley.GestionPresences.entities;


import com.lesley.GestionPresences.Enum.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name="Admin")
public class Administrateur extends User {
    private Role role;
    private List<String> permissions;


}

package com.lesley.GestionPresences.entities;

import com.lesley.GestionPresences.Enum.Role;
import com.lesley.GestionPresences.auth.entities.ForgotPassword;
import com.lesley.GestionPresences.auth.entities.RefreshToken;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Le rôle est obligatoire")
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

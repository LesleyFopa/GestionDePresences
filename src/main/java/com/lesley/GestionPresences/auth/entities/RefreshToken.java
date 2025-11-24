package com.lesley.GestionPresences.auth.entities;

import com.lesley.GestionPresences.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(nullable = false)
    @NotBlank(message = "Entrez une valeur de refresh token ")
    private String refreshToken;

    @Column(nullable = false)
    private Instant expiresAt;

    @OneToOne
    private User user;
}

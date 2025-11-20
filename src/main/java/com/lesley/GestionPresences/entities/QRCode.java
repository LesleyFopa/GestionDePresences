package com.lesley.GestionPresences.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "QRcode")
public class QRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le token est obligatoire")
    @Size(min = 32, max = 64, message = "Le token doit contenir entre 32 et 64 caractères")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Le token ne doit contenir que des caractères alphanumériques")
    private String token;

    @NotBlank(message = "Les données chiffrées sont obligatoires")
    private String donneesChiffres;

    @NotNull(message = "Le timestamp est obligatoire")
    @PastOrPresent(message = "Le timestamp ne peut pas être dans le futur")
    private LocalDateTime timestamp;

    @NotNull(message = "La date d'expiration est obligatoire")
    @Future(message = "La date d'expiration doit être dans le futur")
    private LocalDateTime expiration;

    @NotNull(message = "Le statut d'utilisation est obligatoire")
    private boolean utilise;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    // Validation de durée de vie
    @AssertTrue(message = "Le QR code doit expirer au moins 30 secondes après sa création")
    private boolean isDureeVieValide() {
        if (timestamp == null || expiration == null) return true;
        return Duration.between(timestamp, expiration).getSeconds() >= 30;
    }

    @AssertTrue(message = "Le QR code ne peut pas expirer dans plus de 10 minutes")
    private boolean isExpirationRaisonnable() {
        if (timestamp == null || expiration == null) return true;
        return Duration.between(timestamp, expiration).toMinutes() <= 10;
    }
}

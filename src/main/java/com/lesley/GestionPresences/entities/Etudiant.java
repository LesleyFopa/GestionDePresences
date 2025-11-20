package com.lesley.GestionPresences.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="Etudiants")
public class Etudiant extends User {
    private String matricule;
    private String classe;
    private String groupe;
    private String telephone;
    private boolean qrCodeActif;

    public Etudiant() {}

    public Etudiant(Long id, String nom, String email, String motDePasse, LocalDateTime dateCreation, boolean active, String matricule, String classe, String groupe, String telephone, boolean qrCodeActif) {
        super(id, nom, email, motDePasse, dateCreation, active);
        this.matricule = matricule;
        this.classe = classe;
        this.groupe = groupe;
        this.telephone = telephone;
        this.qrCodeActif = qrCodeActif;
    }

    public Etudiant(String matricule, String classe, String groupe, String telephone, boolean qrCodeActif) {
        this.matricule = matricule;
        this.classe = classe;
        this.groupe = groupe;
        this.telephone = telephone;
        this.qrCodeActif = qrCodeActif;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isQrCodeActif() {
        return qrCodeActif;
    }

    public void setQrCodeActif(boolean qrCodeActif) {
        this.qrCodeActif = qrCodeActif;
    }
}

package com.lesley.GestionPresences.repository;

import com.lesley.GestionPresences.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.motDePasse = ?2 where  u.email =?1")
    void  updateMotDePasse(String email, String newPassword);
}

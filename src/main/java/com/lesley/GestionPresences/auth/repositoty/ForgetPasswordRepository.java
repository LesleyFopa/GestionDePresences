package com.lesley.GestionPresences.auth.repositoty;

import com.lesley.GestionPresences.auth.entities.ForgotPassword;
import com.lesley.GestionPresences.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgotPassword,Integer> {

    @Query("SELECT fp from  ForgotPassword  fp where fp.otp = ?1 and  fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp , User user);

}

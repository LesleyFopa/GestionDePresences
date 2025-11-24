package com.lesley.GestionPresences.controller;

import com.lesley.GestionPresences.auth.entities.ForgotPassword;
import com.lesley.GestionPresences.auth.repositoty.ForgetPasswordRepository;
import com.lesley.GestionPresences.auth.service.EmailService;
import com.lesley.GestionPresences.auth.utils.ChangePassword;
import com.lesley.GestionPresences.auth.utils.MailBody;
import com.lesley.GestionPresences.entities.User;
import com.lesley.GestionPresences.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Controller
@RequestMapping("/api/forgotPassword/")
public class ForgetPasswordController {

    private final UserRepository userRepository;
    private final ForgetPasswordRepository forgetPasswordRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public ForgetPasswordController(UserRepository userRepository, ForgetPasswordRepository forgetPasswordRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.forgetPasswordRepository = forgetPasswordRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    private Integer otpGenerator(){
        Random rand = new Random();
        return rand.nextInt(100000,999999);
    }

    //envoyer un mail de vérification
    @PostMapping("/verification/{email}")
    public ResponseEntity<String> verification(@PathVariable("email") String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("Voici l' OTP pour votre mot de passe oublié" + otp)
                .subject("OTP pour votre mot de passe")
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70*1000))
                .user(user)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgetPasswordRepository.save(forgotPassword);
        return ResponseEntity.ok("OTP pour votre mot de passe");
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable("otp") Integer otp, @PathVariable("email") String email) {
      User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

      ForgotPassword fp = forgetPasswordRepository.findByOtpAndUser(otp,user)
              .orElseThrow(() -> new RuntimeException("OTP invalid"));

      if (fp.getExpirationTime().before(Date.from(Instant.now()))){
          forgetPasswordRepository.deleteById(fp.getForgotPassId());
          return new  ResponseEntity<>("OTP a expiré ", HttpStatus.EXPECTATION_FAILED);

      }
        return ResponseEntity.ok("OTP invalid");
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@PathVariable("email") String email,@RequestBody ChangePassword changePassword) {
        if (!Objects.equals(changePassword.oldPassword(),changePassword.newPassword())){
            return new  ResponseEntity<>("Entrer encore le mot de passe ", HttpStatus.EXPECTATION_FAILED );
        }
        String encordPassword = passwordEncoder.encode(changePassword.oldPassword());
        userRepository.updateMotDePasse(email,encordPassword);
        return ResponseEntity.ok("Mot de passe changé avec succès");
    }
}

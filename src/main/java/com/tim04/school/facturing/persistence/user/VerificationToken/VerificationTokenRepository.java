package com.tim04.school.facturing.persistence.user.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,String> {
    List<VerificationToken> findByUserMail(String email);
    List<VerificationToken> findByToken(String token);

}

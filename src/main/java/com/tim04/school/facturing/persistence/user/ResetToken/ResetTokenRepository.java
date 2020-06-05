package com.tim04.school.facturing.persistence.user.ResetToken;

import com.tim04.school.facturing.persistence.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken,Long> {
    Optional<ResetToken> findByUserMail(String mail);
    Optional<ResetToken> findByToken(String token);
    List<ResetToken> findAllByUserMail(String mail);
    List<ResetToken> findAllByToken(String mail);
    Optional<User> findUserByToken(String token);
    ResetToken findByUser(User user);
}

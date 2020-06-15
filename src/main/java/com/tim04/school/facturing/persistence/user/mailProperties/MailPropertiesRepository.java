package com.tim04.school.facturing.persistence.user.mailProperties;

import com.tim04.school.facturing.persistence.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailPropertiesRepository extends JpaRepository<MailProperties, Long> {
    Optional<MailProperties> findByUser(User user);
    Optional<MailProperties> findByUsername(String username);

}

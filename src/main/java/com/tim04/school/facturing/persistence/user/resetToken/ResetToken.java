package com.tim04.school.facturing.persistence.user.resetToken;

import com.tim04.school.facturing.persistence.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ResetToken")
public class ResetToken {
    public static final String STATUS_PENDING = "UNCHANGED";
    public static final String STATUS_CHANGED = "CHANGED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private Long token_id;
    @Column(name = "token")
    private String token;
    @Column(name = "status")
    private String status;
    @Column(name = "expiredDateTime")
    private LocalDateTime expiredDateTime;
    @Column(name = "issuedDateTime")
    private LocalDateTime issuedDateTime;
    @Column(name = "confirmedDateTime")
    private LocalDateTime confirmedDateTime;
    @OneToOne
    @JoinColumn
    private User user;

    public ResetToken() {
        this.token = UUID.randomUUID().toString();
        this.issuedDateTime = LocalDateTime.now();
        this.expiredDateTime = this.issuedDateTime.plusDays(1);
        this.status = STATUS_PENDING;
    }

    public static String getStatusPending() {
        return STATUS_PENDING;
    }

    public static String getStatusVerified() {
        return STATUS_CHANGED;
    }

    public Long getToken_id() {
        return token_id;
    }

    public void setToken_id(Long token_id) {
        this.token_id = token_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getExpiredDateTime() {
        return expiredDateTime;
    }

    public void setExpiredDateTime(LocalDateTime expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }

    public LocalDateTime getIssuedDateTime() {
        return issuedDateTime;
    }

    public void setIssuedDateTime(LocalDateTime issuedDateTime) {
        this.issuedDateTime = issuedDateTime;
    }

    public LocalDateTime getConfirmedDateTime() {
        return confirmedDateTime;
    }

    public void setConfirmedDateTime(LocalDateTime confirmedDateTime) {
        this.confirmedDateTime = confirmedDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

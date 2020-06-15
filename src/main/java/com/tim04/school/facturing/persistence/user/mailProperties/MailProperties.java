package com.tim04.school.facturing.persistence.user.mailProperties;

import com.tim04.school.facturing.persistence.user.User;

import javax.persistence.*;

@Entity
@Table(name = "MailProp")
public class MailProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prop_id")
    private Long prop_id;
    @Column(name = "hosting")
    private String host;
    @Column(name = "porting")
    private String port;
    @Column(name = "userName")
    private String username;
    @Column(name = "thePassword")
    private String password;
    @Column(name = "smtp_name")
    private String smtp;
    @Column(name = "from_adress")
    private String from;
    @Column(name = "fromNames")
    private String fromName;
    @Column(name = "verificationapis")
    private String verificationapi;
    @Column(name = "resetPwdApi")
    private String resetPwdApi;
    @OneToOne
    @JoinColumn
    @MapsId
    private User user;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getVerificationapi() {
        return verificationapi;
    }

    public void setVerificationapi(String verificationapi) {
        this.verificationapi = verificationapi;
    }

    public Long getProp_id() {
        return prop_id;
    }

    public void setProp_id(Long prop_id) {
        this.prop_id = prop_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public String getResetPwdApi() {
        return resetPwdApi;
    }

    public void setResetPwdApi(String resetPwdApi) {
        this.resetPwdApi = resetPwdApi;
    }

}

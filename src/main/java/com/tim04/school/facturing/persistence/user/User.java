package com.tim04.school.facturing.persistence.user;

import com.tim04.school.facturing.persistence.role.Roles;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.mailProperties.MailProperties;
import com.tim04.school.facturing.persistence.user.verificationToken.VerificationToken;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "mail")
    private String mail;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private Integer age;
    @Column(name = "path")
    private String defaultPath;
    @OneToMany( mappedBy="user",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Supplier> suppliers = new ArrayList<>();
    @Column(name = "role")
    private Roles roles;
    @Column(name = "isActive")
    private Boolean isActive;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private VerificationToken verificationToken;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private MailProperties mailProperties;

    public MailProperties getMailProperties() {
        return mailProperties;
    }

    public void setMailProperties(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Roles getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        this.isActive = active;
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }


}

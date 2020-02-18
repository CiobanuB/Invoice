package com.tim04.school.facturing.persistence.user;

import com.tim04.school.facturing.persistence.Role.Role;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long userID;
    @Column(name = "firstName")
    @NotEmpty(message = "Please insert your First Name")
    private String firstName;
    @Column(name = "lastName")
    //@NotEmpty(message = "Please insert your Last Name")
    private String lastName;
    @Column(name = "mail")
    @NotEmpty(message = "Please insert your mail")
    private String mail;
    @Column(name = "password")
    @NotEmpty(message = "Please insert your password")
    private String password;
    @Column(name = "age")
    private int age;
    //TODO: Documentare despre FetchType EAGER vs LAZY
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
    private Set<Role> roles;

    public Long getID() {
        return userID;
    }

    public void setID(Long ID) {
        this.userID = ID;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

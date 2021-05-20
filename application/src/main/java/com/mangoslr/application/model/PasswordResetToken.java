package com.mangoslr.application.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name ="PasswordResetToken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true,length = 200,nullable = false)
    private String token;
    @Column(length = 100,nullable = false)
    private String email;
    @Column(length = 30,nullable = false)
    private Date expirationDate;
    @Column(nullable = false)
    private Boolean tokenUsed;

    //Constructores
    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(String token, String email) {
        this.token = token;
        this.email = email;
        this.expirationDate = expiracionDate();//Puedo impotar parametros en Application Propieties
        this.tokenUsed = false;
    }
    public Date expiracionDate(){
        Date date = new Date();
        date.setYear(date.getYear());
        date.setMonth(date.getMonth());
        date.setDate(date.getDate()+1);
        return date;
    }
    // Setter/Getter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getTokenUsed() {
        return tokenUsed;
    }

    public void setTokenUsed(Boolean tokenUsed) {
        this.tokenUsed = tokenUsed;
    }

}


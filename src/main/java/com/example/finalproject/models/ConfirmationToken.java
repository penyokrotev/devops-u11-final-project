package com.example.finalproject.models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "conf_token_table")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int tokenId;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = SecurityUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_mail")
    private SecurityUser securityUser;

    public ConfirmationToken() {

    }

    public ConfirmationToken(SecurityUser securityUser) {
        this.securityUser = securityUser;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public SecurityUser getUser() {
        return securityUser;
    }

    public void setUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }
}
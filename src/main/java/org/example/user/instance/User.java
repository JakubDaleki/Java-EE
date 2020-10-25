package org.example.user.instance;

import lombok.Data;
import org.example.utils.Sha512Utility;

import java.util.Date;
import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String login;
    private byte[] passwordHash;
    private String name;
    private String surname;
    private Date creationDate;

    public User(String login, String password, String name, String surname) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.passwordHash = Sha512Utility.hash(password);
        this.name = name;
        this.surname = surname;
        this.creationDate = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return Sha512Utility.bytesToHex(passwordHash);
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}

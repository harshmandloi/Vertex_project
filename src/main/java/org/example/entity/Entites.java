package org.example.entity;

import io.vertx.core.json.JsonObject;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "storeddata")
public class Entites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "password", unique = true)
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name = "refreshToken") // Add this line
    private String refreshToken; // Add this line

    public Date getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(Date tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public Date getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

    public void setRefreshTokenExpiry(Date refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    @Column(name = "token_expiry")
    private Date tokenExpiry;

    @Column(name = "refresh_token_expiry")
    private Date refreshTokenExpiry;

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() { // Add this method
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) { // Add this method
        this.refreshToken = refreshToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JsonObject toJsonObject() {
        return JsonObject.mapFrom(this);
    }
}

package com.n19dccn112.model.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
@Getter
@Setter
public class JwtResponse {
    private String token;
    @JsonIgnore
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String phone;
    private String email;
    private String role;
    private String name;
    private String address;

    public JwtResponse(String token, Long userId, String username, String phone,
                       String email, String role, String name, String address) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.name = name;
        this.address = address;
    }
}

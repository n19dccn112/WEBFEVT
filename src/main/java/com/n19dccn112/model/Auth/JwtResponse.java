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
    private Long user_id;
    private String username;
    private String phone;
    private String email;
    private String role;

    public JwtResponse(String token, Long user_id, String username, String phone, String email, String role) {
        this.token = token;
        this.user_id = user_id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }
}

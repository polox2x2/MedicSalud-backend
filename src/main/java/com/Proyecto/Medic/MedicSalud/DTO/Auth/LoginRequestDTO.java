package com.Proyecto.Medic.MedicSalud.DTO.Auth;

import lombok.Data;

import java.util.Set;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;

    @Data
    public class JwtResponse {
        private String token;
        private String type = "Bearer";
        private String email;
        private Set<String> roles;

        public JwtResponse(String token, String email, Set<String> roles) {
            this.token = token;
            this.email = email;
            this.roles = roles;
        }
}
     }

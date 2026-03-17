package com.example.tuan3_tts_th04395.entity.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String email;
    private String password;

}

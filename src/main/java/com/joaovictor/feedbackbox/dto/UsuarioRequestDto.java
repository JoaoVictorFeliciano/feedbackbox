package com.joaovictor.feedbackbox.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDto(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Informe um e-mail válido")
        String email

) {
}


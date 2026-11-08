package com.joaovictor.feedbackbox.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RespostaRequestDto(

        @NotBlank(message = "O ID do feedback é obrigatório")
        String feedbackId,

        @NotBlank(message = "A mensagem é obrigatória")
        @Size(min = 3, message = "A resposta deve ter pelo menos 3 caracteres")
        String mensagem,

        @NotBlank(message = "O responsável é obrigatório")
        String responsavel

) {
}

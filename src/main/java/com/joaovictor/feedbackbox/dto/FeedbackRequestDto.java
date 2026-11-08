
package com.joaovictor.feedbackbox.dto;

import com.joaovictor.feedbackbox.enumerate.CategoriaFeedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeedbackRequestDto(

        @NotBlank(message = "O ID do usuário é obrigatório")
        String usuarioId,

        @NotBlank(message = "O título é obrigatório")
        @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
        String titulo,

        @NotBlank(message = "A mensagem é obrigatória")
        @Size(min = 10, message = "A mensagem deve ter pelo menos 10 caracteres")
        String mensagem,

        @NotNull(message = "A categoria é obrigatória")
        CategoriaFeedback categoria

) {
}

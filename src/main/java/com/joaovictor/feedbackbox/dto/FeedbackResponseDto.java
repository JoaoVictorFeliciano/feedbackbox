
package com.joaovictor.feedbackbox.dto;

import com.joaovictor.feedbackbox.enumerate.CategoriaFeedback;
import com.joaovictor.feedbackbox.enumerate.StatusFeedback;

import java.time.LocalDateTime;

public record FeedbackResponseDto(
        String id,
        String usuarioId,
        String titulo,
        String mensagem,
        CategoriaFeedback categoria,
        StatusFeedback status,
        LocalDateTime dataCriacao
) {
}

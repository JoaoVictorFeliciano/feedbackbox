
package com.joaovictor.feedbackbox.dto;

import java.time.LocalDateTime;

public record RespostaResponseDto(
        String id,
        String feedbackId,
        String mensagem,
        String responsavel,
        LocalDateTime dataResposta
) {
}

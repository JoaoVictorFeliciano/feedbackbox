
package com.joaovictor.feedbackbox.dto;

import java.time.LocalDateTime;

public record UsuarioResponseDto(
        String id,
        String nome,
        String email,
        LocalDateTime dataCadastro
) {
}

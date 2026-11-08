
package com.joaovictor.feedbackbox.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI feedbackBoxOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FeedbackBox API")
                        .version("1.0.0")
                        .description("""
                                API REST para gerenciamento de feedbacks.

                                Funcionalidades:
                                - Cadastro e gerenciamento de usuários.
                                - Cadastro e gerenciamento de feedbacks.
                                - Classificação de feedbacks por categoria.
                                - Controle de status dos feedbacks.
                                - Cadastro e gerenciamento de respostas.
                                """));
    }
}


package com.joaovictor.feedbackbox.model;

import com.joaovictor.feedbackbox.enumerate.CategoriaFeedback;
import com.joaovictor.feedbackbox.enumerate.StatusFeedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.data.couchbase.repository.Collection;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
@Scope("app")
@Collection("feedbacks")
public class Feedback {

    @Id
    private String id;

    @Field
    private String usuarioId;

    @Field
    private String titulo;

    @Field
    private String mensagem;

    @Field
    private CategoriaFeedback categoria;

    @Field
    private StatusFeedback status;

    @Field
    private LocalDateTime dataCriacao;
}

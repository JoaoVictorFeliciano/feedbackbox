
package com.joaovictor.feedbackbox.model;

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
@Collection("respostas")
public class Resposta {

    @Id
    private String id;

    @Field
    private String feedbackId;

    @Field
    private String mensagem;

    @Field
    private String responsavel;

    @Field
    private LocalDateTime dataResposta;
}

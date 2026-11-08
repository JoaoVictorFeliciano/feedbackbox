
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
@Collection("usuarios")
public class Usuario {

    @Id
    private String id;

    @Field
    private String nome;

    @Field
    private String email;

    @Field
    private LocalDateTime dataCadastro;
}

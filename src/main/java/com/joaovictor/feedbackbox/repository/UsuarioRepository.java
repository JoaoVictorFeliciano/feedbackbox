package com.joaovictor.feedbackbox.repository;

import com.joaovictor.feedbackbox.model.Usuario;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface UsuarioRepository extends CouchbaseRepository<Usuario, String> {
    boolean existsByEmail(String email);
}


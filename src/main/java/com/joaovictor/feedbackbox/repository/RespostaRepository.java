
package com.joaovictor.feedbackbox.repository;

import com.joaovictor.feedbackbox.model.Resposta;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

public interface RespostaRepository extends CouchbaseRepository<Resposta, String> {

    List<Resposta> findAllByFeedbackId(String feedbackId);

}


package com.joaovictor.feedbackbox.repository;

import com.joaovictor.feedbackbox.enumerate.CategoriaFeedback;
import com.joaovictor.feedbackbox.enumerate.StatusFeedback;
import com.joaovictor.feedbackbox.model.Feedback;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

public interface FeedbackRepository extends CouchbaseRepository<Feedback, String> {

    List<Feedback> findAllByCategoria(CategoriaFeedback categoria);

    List<Feedback> findAllByStatus(StatusFeedback status);
}

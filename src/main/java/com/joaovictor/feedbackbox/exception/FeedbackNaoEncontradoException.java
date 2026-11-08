package com.joaovictor.feedbackbox.exception;

public class FeedbackNaoEncontradoException extends RuntimeException {

    public FeedbackNaoEncontradoException() {
        super("Feedback não encontrado");
    }
}

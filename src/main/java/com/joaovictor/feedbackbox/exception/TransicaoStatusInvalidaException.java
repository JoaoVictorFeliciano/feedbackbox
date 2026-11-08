package com.joaovictor.feedbackbox.exception;

public class TransicaoStatusInvalidaException extends RuntimeException {

    public TransicaoStatusInvalidaException() {
        super("Não é permitido retornar um feedback resolvido para pendente");
    }
}

package com.joaovictor.feedbackbox.exception;

public class RespostaNaoEncontradaException extends RuntimeException {

    public RespostaNaoEncontradaException() {
        super("Resposta não encontrada");
    }
}

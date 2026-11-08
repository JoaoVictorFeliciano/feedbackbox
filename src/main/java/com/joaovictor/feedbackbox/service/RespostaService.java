
package com.joaovictor.feedbackbox.service;

import com.joaovictor.feedbackbox.dto.RespostaRequestDto;
import com.joaovictor.feedbackbox.dto.RespostaResponseDto;
import com.joaovictor.feedbackbox.exception.FeedbackNaoEncontradoException;
import com.joaovictor.feedbackbox.exception.RespostaNaoEncontradaException;
import com.joaovictor.feedbackbox.model.Resposta;
import com.joaovictor.feedbackbox.repository.FeedbackRepository;
import com.joaovictor.feedbackbox.repository.RespostaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RespostaService {

    private final RespostaRepository respostaRepository;
    private final FeedbackRepository feedbackRepository;

    public RespostaService(
            RespostaRepository respostaRepository,
            FeedbackRepository feedbackRepository) {
        this.respostaRepository = respostaRepository;
        this.feedbackRepository = feedbackRepository;
    }


    public RespostaResponseDto cadastrar(RespostaRequestDto dto) {

        if (!feedbackRepository.existsById(dto.feedbackId())) {
            throw new FeedbackNaoEncontradoException();
        }

        Resposta resposta = Resposta.builder()
                .id(UUID.randomUUID().toString())
                .feedbackId(dto.feedbackId())
                .mensagem(dto.mensagem())
                .responsavel(dto.responsavel())
                .dataResposta(LocalDateTime.now())
                .build();

        Resposta respostaSalva = respostaRepository.save(resposta);

        return converterParaResponse(respostaSalva);
    }


    public List<RespostaResponseDto> listarTodas() {
        return respostaRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }


    public List<RespostaResponseDto> listarPorFeedback(String feedbackId) {

        if (!feedbackRepository.existsById(feedbackId)) {
            throw new FeedbackNaoEncontradoException();
        }

        return respostaRepository.findAllByFeedbackId(feedbackId)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }


    public RespostaResponseDto buscarPorId(String id) {

        Resposta resposta = respostaRepository.findById(id)
                .orElseThrow(RespostaNaoEncontradaException::new);

        return converterParaResponse(resposta);
    }


    public void excluir(String id) {

        if (!respostaRepository.existsById(id)) {
            throw new RespostaNaoEncontradaException();
        }

        respostaRepository.deleteById(id);
    }


    private RespostaResponseDto converterParaResponse(Resposta resposta) {

        return new RespostaResponseDto(
                resposta.getId(),
                resposta.getFeedbackId(),
                resposta.getMensagem(),
                resposta.getResponsavel(),
                resposta.getDataResposta()
        );
    }
}

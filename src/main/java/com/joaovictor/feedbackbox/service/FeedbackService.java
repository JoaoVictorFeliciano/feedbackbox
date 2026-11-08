
package com.joaovictor.feedbackbox.service;

import com.joaovictor.feedbackbox.dto.FeedbackRequestDto;
import com.joaovictor.feedbackbox.dto.FeedbackResponseDto;
import com.joaovictor.feedbackbox.exception.FeedbackNaoEncontradoException;
import com.joaovictor.feedbackbox.exception.TransicaoStatusInvalidaException;
import com.joaovictor.feedbackbox.exception.UsuarioNaoEncontradoException;
import com.joaovictor.feedbackbox.enumerate.CategoriaFeedback;
import com.joaovictor.feedbackbox.enumerate.StatusFeedback;
import com.joaovictor.feedbackbox.model.Feedback;
import com.joaovictor.feedbackbox.repository.FeedbackRepository;
import com.joaovictor.feedbackbox.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UsuarioRepository usuarioRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            UsuarioRepository usuarioRepository) {
        this.feedbackRepository = feedbackRepository;
        this.usuarioRepository = usuarioRepository;
    }


    public FeedbackResponseDto cadastrar(FeedbackRequestDto dto) {

        if (!usuarioRepository.existsById(dto.usuarioId())) {
            throw new UsuarioNaoEncontradoException();
        }

        Feedback feedback = Feedback.builder()
                .id(UUID.randomUUID().toString())
                .usuarioId(dto.usuarioId())
                .titulo(dto.titulo())
                .mensagem(dto.mensagem())
                .categoria(dto.categoria())
                .status(StatusFeedback.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .build();

        Feedback feedbackSalvo = feedbackRepository.save(feedback);

        return converterParaResponse(feedbackSalvo);
    }


    public List<FeedbackResponseDto> listarTodos() {
        return feedbackRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }


    public FeedbackResponseDto buscarPorId(String id) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(FeedbackNaoEncontradoException::new);

        return converterParaResponse(feedback);
    }


    public List<FeedbackResponseDto> listarPorCategoria(CategoriaFeedback categoria) {

        return feedbackRepository.findAllByCategoria(categoria)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }


    public List<FeedbackResponseDto> listarPorStatus(StatusFeedback status) {

        return feedbackRepository.findAllByStatus(status)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }


    public FeedbackResponseDto atualizarStatus(String id, StatusFeedback novoStatus) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(FeedbackNaoEncontradoException::new);

        if (feedback.getStatus() == StatusFeedback.RESOLVIDO
                && novoStatus == StatusFeedback.PENDENTE) {
            throw new TransicaoStatusInvalidaException();
        }

        feedback.setStatus(novoStatus);

        Feedback feedbackAtualizado = feedbackRepository.save(feedback);

        return converterParaResponse(feedbackAtualizado);
    }


    public void excluir(String id) {

        if (!feedbackRepository.existsById(id)) {
            throw new FeedbackNaoEncontradoException();
        }

        feedbackRepository.deleteById(id);
    }


    private FeedbackResponseDto converterParaResponse(Feedback feedback) {

        return new FeedbackResponseDto(
                feedback.getId(),
                feedback.getUsuarioId(),
                feedback.getTitulo(),
                feedback.getMensagem(),
                feedback.getCategoria(),
                feedback.getStatus(),
                feedback.getDataCriacao()
        );
    }
}

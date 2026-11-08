
package com.joaovictor.feedbackbox.controller;

import com.joaovictor.feedbackbox.dto.FeedbackRequestDto;
import com.joaovictor.feedbackbox.dto.FeedbackResponseDto;
import com.joaovictor.feedbackbox.enumerate.CategoriaFeedback;
import com.joaovictor.feedbackbox.enumerate.StatusFeedback;
import com.joaovictor.feedbackbox.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @PostMapping
    public ResponseEntity<FeedbackResponseDto> cadastrar(
            @RequestBody @Valid FeedbackRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(feedbackService.cadastrar(dto));
    }


    @GetMapping
    public ResponseEntity<List<FeedbackResponseDto>> listarTodos() {

        return ResponseEntity.ok(feedbackService.listarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> buscarPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(feedbackService.buscarPorId(id));
    }


    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<FeedbackResponseDto>> listarPorCategoria(
            @PathVariable CategoriaFeedback categoria) {

        return ResponseEntity.ok(
                feedbackService.listarPorCategoria(categoria));
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<FeedbackResponseDto>> listarPorStatus(
            @PathVariable StatusFeedback status) {

        return ResponseEntity.ok(
                feedbackService.listarPorStatus(status));
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<FeedbackResponseDto> atualizarStatus(
            @PathVariable String id,
            @RequestParam StatusFeedback novoStatus) {

        return ResponseEntity.ok(
                feedbackService.atualizarStatus(id, novoStatus));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {

        feedbackService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

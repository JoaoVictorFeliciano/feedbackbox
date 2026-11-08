
package com.joaovictor.feedbackbox.controller;

import com.joaovictor.feedbackbox.dto.RespostaRequestDto;
import com.joaovictor.feedbackbox.dto.RespostaResponseDto;
import com.joaovictor.feedbackbox.service.RespostaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respostas")
public class RespostaController {

    private final RespostaService respostaService;

    public RespostaController(RespostaService respostaService) {
        this.respostaService = respostaService;
    }


    @PostMapping
    public ResponseEntity<RespostaResponseDto> cadastrar(
            @RequestBody @Valid RespostaRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respostaService.cadastrar(dto));
    }


    @GetMapping
    public ResponseEntity<List<RespostaResponseDto>> listarTodas() {

        return ResponseEntity.ok(respostaService.listarTodas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<RespostaResponseDto> buscarPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(respostaService.buscarPorId(id));
    }


    @GetMapping("/feedback/{feedbackId}")
    public ResponseEntity<List<RespostaResponseDto>> listarPorFeedback(
            @PathVariable String feedbackId) {

        return ResponseEntity.ok(
                respostaService.listarPorFeedback(feedbackId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {

        respostaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

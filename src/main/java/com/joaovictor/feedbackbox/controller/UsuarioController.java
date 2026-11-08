
package com.joaovictor.feedbackbox.controller;

import com.joaovictor.feedbackbox.dto.UsuarioRequestDto;
import com.joaovictor.feedbackbox.dto.UsuarioResponseDto;
import com.joaovictor.feedbackbox.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrar(
            @RequestBody @Valid UsuarioRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.cadastrar(dto));
    }


    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarTodos() {

        return ResponseEntity.ok(usuarioService.listarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {

        usuarioService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

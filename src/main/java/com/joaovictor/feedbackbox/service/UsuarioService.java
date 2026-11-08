
package com.joaovictor.feedbackbox.service;

import com.joaovictor.feedbackbox.dto.UsuarioRequestDto;
import com.joaovictor.feedbackbox.dto.UsuarioResponseDto;
import com.joaovictor.feedbackbox.exception.EmailJaCadastradoException;
import com.joaovictor.feedbackbox.exception.UsuarioNaoEncontradoException;
import com.joaovictor.feedbackbox.model.Usuario;
import com.joaovictor.feedbackbox.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public UsuarioResponseDto cadastrar(UsuarioRequestDto dto) {

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException();
        }

        Usuario usuario = Usuario.builder()
                .id(UUID.randomUUID().toString())
                .nome(dto.nome())
                .email(dto.email())
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return converterParaResponse(usuarioSalvo);
    }


    public List<UsuarioResponseDto> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }


    public UsuarioResponseDto buscarPorId(String id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        return converterParaResponse(usuario);
    }


    public void excluir(String id) {

        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNaoEncontradoException();
        }

        usuarioRepository.deleteById(id);
    }


    private UsuarioResponseDto converterParaResponse(Usuario usuario) {

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCadastro()
        );
    }
}

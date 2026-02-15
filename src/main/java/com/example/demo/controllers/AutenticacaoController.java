package com.example.demo.controllers;

import com.example.demo.dtos.ApiResponse;
import com.example.demo.dtos.AutenticacaoDTO;
import com.example.demo.dtos.LoginResponseDTO;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody AutenticacaoDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

            return ResponseEntity
                    .ok(new ApiResponse<>(true, "Login realizado com sucesso", new LoginResponseDTO(token)));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Falha na autenticação", null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody AutenticacaoDTO data) {
        if (this.repository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Usuário já existe", null));
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(null, data.login(), encryptedPassword, "ROLE_USER");
        this.repository.save(novoUsuario);

        return ResponseEntity.ok(new ApiResponse<>(true, "Usuário criado com sucesso", null));
    }
}
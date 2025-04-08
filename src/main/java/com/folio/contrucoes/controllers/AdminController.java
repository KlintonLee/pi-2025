package com.folio.contrucoes.controllers;

import com.folio.contrucoes.dtos.AtualizarSenhaAdminDto;
import com.folio.contrucoes.dtos.UsuarioSenhaDto;
import com.folio.contrucoes.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
public class AdminController implements AdminApi {

    @Autowired
    private AdminService adminService;

    @Override
    public ResponseEntity<Object> autenticar(UsuarioSenhaDto dto) throws Exception {
        return ResponseEntity.ok(adminService.autenticar(dto.email, dto.senha));
    }

    @Override
    public ResponseEntity<Object> atualizarSenha(AtualizarSenhaAdminDto dto) {
        adminService.atualizarSenhaAdmin(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("internal/init")
    public ResponseEntity<Object> initialize() {
        adminService.criarAdmin();
        return ResponseEntity.noContent().build();
    }
}

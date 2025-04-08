package com.folio.contrucoes.services;

import com.folio.contrucoes.dtos.AtualizarSenhaAdminDto;
import com.folio.contrucoes.exception.UnauthorizedException;
import com.folio.contrucoes.exception.UnprocessableEntityException;
import com.folio.contrucoes.models.Admin;
import com.folio.contrucoes.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

import static com.folio.contrucoes.services.AuthorizationService.ADMIN_ID;
import static com.folio.contrucoes.services.AuthorizationService.TOKEN_ID;

@Service
public class AdminService {

    private static final String USUARIO_INICIAL = "admin@admin.com";
    private static final String SENHA_INICIAL = "123456";
    private static final SecureRandom random = new SecureRandom();

    @Autowired
    private AdminRepository adminRepository;

    public void criarAdmin() {
        if (!this.adminRepository.existsById(ADMIN_ID)) {
            Admin token = new Admin();
            token.setId(ADMIN_ID);
            token.setEmail(USUARIO_INICIAL);
            token.setPassword(encoder(SENHA_INICIAL));
            token.setCreatedAt(Instant.now());
            adminRepository.save(token);
        }
    }

    public String autenticar(String email, String senha) throws Exception {
        return this.adminRepository.findById(ADMIN_ID).map(adm -> {
            if (!Objects.equals(email, adm.getEmail())) {
                throw new UnauthorizedException("Usuario ou senha inválidos");
            }

            if (!Objects.equals(encoder(senha), adm.getPassword())) {
                throw new UnauthorizedException("Usuario ou senha inválidos");
            }

            Admin token = new Admin();
            token.setId(TOKEN_ID);
            token.setEmail(encoder(senha));
            token.setPassword(gerarHashAleatorio());
            token.setCreatedAt(Instant.now());
            adminRepository.save(token);
            return token.getPassword();
        }).orElseThrow(Exception::new);
    }

    public void atualizarSenhaAdmin(AtualizarSenhaAdminDto atualizarSenhaAdminDto) {
        if (atualizarSenhaAdminDto.senhaAtual == null || atualizarSenhaAdminDto.senhaAtual.isEmpty()) {
            throw new UnprocessableEntityException("Senha atual não pode ser nula ou vazia");
        }

        if (atualizarSenhaAdminDto.novaSenha == null || atualizarSenhaAdminDto.novaSenha.isEmpty()) {
            throw new UnprocessableEntityException("Nova senha não pode ser nula ou vazia");
        }

        if (!Objects.equals(atualizarSenhaAdminDto.novaSenha, atualizarSenhaAdminDto.confirmarNovaSenha)) {
            throw new UnprocessableEntityException("Nova senha e confirmação de nova senha não coincidem");
        }

        this.adminRepository.findById(ADMIN_ID).ifPresent(admin -> {
            admin.setPassword(encoder(atualizarSenhaAdminDto.novaSenha));
            this.adminRepository.save(admin);
        });
    }

    private static String encoder(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public String gerarHashAleatorio() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        StringBuilder hash = new StringBuilder();
        for (byte b : bytes) {
            hash.append(String.format("%02x", b));
        }
        return hash.toString();
    }
}

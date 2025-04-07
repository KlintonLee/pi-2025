package com.folio.contrucoes.services;

import com.folio.contrucoes.dtos.AtualizarSenhaAdminDto;
import com.folio.contrucoes.exception.UnauthorizedException;
import com.folio.contrucoes.exception.UnprocessableEntityException;
import com.folio.contrucoes.models.Admin;
import com.folio.contrucoes.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Objects;

import static com.folio.contrucoes.services.AuthorizationService.ADMIN_ID;
import static com.folio.contrucoes.services.AuthorizationService.TOKEN_ID;

public class AdminService {


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final SecureRandom random = new SecureRandom();

    @Autowired
    private AdminRepository adminRepository;

    public String autenticar(String email, String senha) throws Exception {
        return this.adminRepository.findById(ADMIN_ID).map(adm -> {
            if (!Objects.equals(email, adm.getEmail())) {
                throw new UnauthorizedException("Usuario ou senha inválidos");
            }

            if (!Objects.equals(encoder.encode(senha), adm.getPassword())) {
                throw new UnauthorizedException("Usuario ou senha inválidos");
            }

            Admin token = new Admin();
            token.setId(TOKEN_ID);
            token.setEmail(adm.getEmail());
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
            admin.setPassword(encoder.encode(atualizarSenhaAdminDto.novaSenha));
            this.adminRepository.save(admin);
        });
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

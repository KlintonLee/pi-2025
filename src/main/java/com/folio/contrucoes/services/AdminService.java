package com.folio.contrucoes.services;

import com.folio.contrucoes.dtos.AtualizarSenhaAdminDto;
import com.folio.contrucoes.exception.UnprocessableEntityException;
import com.folio.contrucoes.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class AdminService {

    private static final Integer ADMIN_ID = 1;

    @Autowired
    private AdminRepository adminRepository;

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
            admin.setPassword(atualizarSenhaAdminDto.novaSenha);
            this.adminRepository.save(admin);
        });
    }
}

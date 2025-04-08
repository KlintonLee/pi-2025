package com.folio.contrucoes.services;

import com.folio.contrucoes.exception.UnauthorizedException;
import com.folio.contrucoes.models.Admin;
import com.folio.contrucoes.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthorizationService {

    public static final Integer ADMIN_ID = 1;
    public static final Integer TOKEN_ID = 2;

    @Autowired
    private AdminRepository adminRepository;

    public void autenticar(String token) {
        Admin admToken = adminRepository.findByPassword(token)
                .orElseThrow(() -> new UnauthorizedException("Token inválido"));
        Instant now = Instant.now();

        if (now.isAfter(admToken.getCreatedAt().plusSeconds(3600*24))) {
            this.adminRepository.delete(admToken);
            throw new UnauthorizedException("Token expirado");
        }
    }
}

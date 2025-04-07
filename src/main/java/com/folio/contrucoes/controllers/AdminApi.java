package com.folio.contrucoes.controllers;

import com.folio.contrucoes.dtos.AtualizarSenhaAdminDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/admins")
public interface AdminApi {
    @PatchMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Atualiza um Feed pelo seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feed atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "422", description = "Um erro de validação foi lançado"),
            @ApiResponse(responseCode = "500", description = "Um erro inexperado ocorreu no servidor")
    })
    ResponseEntity<Object> atualizarSenha(@RequestBody AtualizarSenhaAdminDto dto);
}

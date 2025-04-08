package com.folio.contrucoes.controllers;

import com.folio.contrucoes.dtos.AdicionarImagemDto;
import com.folio.contrucoes.dtos.CriarAtualizarFeedDto;
import com.folio.contrucoes.dtos.FeedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Feeds")
@RequestMapping("/v1/feeds")
public interface FeedApi {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Cria um novo feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feed criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "422", description = "Um erro de validação foi lançado"),
            @ApiResponse(responseCode = "500", description = "Um erro inexperado ocorreu no servidor")
    })
    ResponseEntity<Object> criarFeed(@RequestHeader(value = "x-auth-token", required = false) String token,
                                     @RequestBody CriarAtualizarFeedDto dto);

    @GetMapping
    @Operation(summary = "Lista todos feeds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "422", description = "Um erro de validação dos parâmetros foi lançado"),
            @ApiResponse(responseCode = "500", description = "Um erro inexperado ocorreu no servidor"),
    })
    ResponseEntity<List<FeedResponse>> listarFeeds(@RequestHeader(value = "x-auth-token", required = false) String token);

    @PutMapping(
            value = "{idFeed}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Atualiza um Feed pelo seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feed atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Feed não localizado"),
            @ApiResponse(responseCode = "422", description = "Um erro de validação foi lançado"),
            @ApiResponse(responseCode = "500", description = "Um erro inexperado ocorreu no servidor")
    })
    ResponseEntity<Object> atualizarFeed(@RequestHeader(value = "x-auth-token", required = false) String token,
                                         @PathVariable Integer idFeed,
                                         @RequestBody CriarAtualizarFeedDto dto);

    @DeleteMapping("{idFeed}")
    @Operation(summary = "Deleta um feed pelo seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Feed deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Feed não localizado"),
            @ApiResponse(responseCode = "500", description = "Um erro inexperado ocorreu no servidor")
    })
    ResponseEntity<Void> deletarFeed(@RequestHeader(value = "x-auth-token", required = false) String token,
                                     @PathVariable("idFeed") Integer idFeed);

    @PostMapping(
            value = "imagens",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Envia uma url de imagem para vincular ao Feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem vinculada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "422", description = "Um erro de validação foi lançado"),
            @ApiResponse(responseCode = "500", description = "Um erro inexperado ocorreu no servidor")
    })
    ResponseEntity<Object> criarImagem(@RequestHeader(value = "x-auth-token", required = false) String token,
                                       @RequestBody AdicionarImagemDto dto);

    @DeleteMapping(value = "imagens/{id}")
    @Operation(summary = "Deleta uma imagem do feed pelo seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Image deletada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Feed não localizado"),
            @ApiResponse(responseCode = "500", description = "Um erro inexperado ocorreu no servidor")
    })
    ResponseEntity<Void> deletarImagem(@RequestHeader(value = "x-auth-token", required = false) String token,
                                       @PathVariable Integer id);
}

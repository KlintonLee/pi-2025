package com.folio.contrucoes.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AtualizarSenhaAdminDto {
    @JsonProperty("senha_atual")
    public String senhaAtual;
    @JsonProperty("nova_senha")
    public String novaSenha;
    @JsonProperty("confirmar_senha")
    public String confirmarNovaSenha;
}

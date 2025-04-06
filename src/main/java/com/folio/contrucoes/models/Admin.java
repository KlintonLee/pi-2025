package com.folio.contrucoes.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class Admin {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 64)
    private String password;
}

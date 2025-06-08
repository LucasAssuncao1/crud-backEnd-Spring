package com.lucas.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("Registro não encontrado com o id: " + id);
    }
}

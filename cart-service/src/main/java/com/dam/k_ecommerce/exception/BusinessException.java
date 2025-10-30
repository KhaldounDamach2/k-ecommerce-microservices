package com.dam.k_ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String msg;

    // Konstruktor mit nur einer Nachricht
    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    // Konstruktor mit Nachricht und Ursache
    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }
}

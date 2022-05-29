package com.ecommerce.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseError implements Serializable {
    @Serial
    private static final long serialVersionUID = -6007549440926446809L;

    private String id = "-";
    private String message = "-";
}

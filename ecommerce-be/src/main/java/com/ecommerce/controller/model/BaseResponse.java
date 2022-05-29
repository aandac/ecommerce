package com.ecommerce.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -765890512332070692L;

    private Object payload;
    private List<BaseError> errors = new ArrayList<>();
}

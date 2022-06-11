package com.ecommerce.controller;

import com.ecommerce.controller.model.BaseError;
import com.ecommerce.controller.model.BaseResponse;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;

@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class, SignatureException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    protected BaseResponse handleAuthenticationException(BadCredentialsException ex) {
        return BaseResponse.builder()
                .errors(
                        Collections.singletonList(
                                BaseError.builder()
                                        .id("error-credentials")
                                        .message(ex.getMessage())
                                        .build())
                )
                .build();
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected BaseResponse handleFileSizeException(FileSizeLimitExceededException ex) {
        return BaseResponse.builder()
                .errors(
                        Collections.singletonList(
                                BaseError.builder()
                                        .id("file-size-exceeded")
                                        .message(ex.getMessage())
                                        .build())
                )
                .build();
    }


    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    protected ResponseEntity<BaseResponse> handleFileSizeException(HttpClientErrorException ex) {
        BaseResponse response = BaseResponse.builder()
                .errors(
                        Collections.singletonList(
                                BaseError.builder()
                                        .id(String.valueOf(ex.getRawStatusCode()))
                                        .message(ex.getMessage())
                                        .build())
                )
                .build();
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected BaseResponse handleException(Exception ex) {
        log.error("Internal server error", ex);
        return BaseResponse.builder()
                .errors(
                        Collections.singletonList(
                                BaseError.builder()
                                        .id("internal-server-error")
                                        .message(ex.getMessage())
                                        .build())
                )
                .build();
    }

}

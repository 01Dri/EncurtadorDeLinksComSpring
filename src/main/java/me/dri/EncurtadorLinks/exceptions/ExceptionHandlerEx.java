package me.dri.EncurtadorLinks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerEx {

    @ExceptionHandler(UrlShortenerExpired.class)
    public ResponseEntity<ExceptionResponse> conteudoNaoEncontrado(UrlShortenerExpired e) {
        String error = "";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse err = new ExceptionResponse(new Date(), error, e.getMessage(), status.value());
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(UrlFormatInvalid.class)
    public ResponseEntity<ExceptionResponse> conteudoNaoEncontrado(UrlFormatInvalid e) {
        String error = "";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse err = new ExceptionResponse(new Date(), error, e.getMessage(), status.value());
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(NotFoundUrl.class)
    public ResponseEntity<ExceptionResponse> conteudoNaoEncontrado(NotFoundUrl e) {
        String error = "";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse err = new ExceptionResponse(new Date(), error, e.getMessage(), status.value());
        return ResponseEntity.status(status).body(err);
    }
}

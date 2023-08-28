package me.dri.EncurtadorLinks.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorWhileGenerateUrlShortener extends  RuntimeException {

    public ErrorWhileGenerateUrlShortener(String msg) {
        super(msg);
    }
}

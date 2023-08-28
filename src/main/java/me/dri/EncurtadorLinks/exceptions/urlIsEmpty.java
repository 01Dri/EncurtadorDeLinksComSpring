package me.dri.EncurtadorLinks.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class urlIsEmpty extends  RuntimeException {

    public urlIsEmpty(String msg) {
        super(msg);
    }
}

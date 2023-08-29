package me.dri.EncurtadorLinks.exceptions;

import java.util.Date;

public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;

    private int errostatus;

    public ExceptionResponse() {

    }
    public ExceptionResponse(Date timestamp, String message, String details, int errostatus) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.errostatus = errostatus;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getErrostatus() {
        return errostatus;
    }

    public void setErrostatus(int errostatus) {
        this.errostatus = errostatus;
    }
}
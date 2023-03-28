package com.example.Trustifyrest;

public class ErrorMessage {
    private String errorMessage;

    public ErrorMessage( String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String err){
        errorMessage = err;
    }

}

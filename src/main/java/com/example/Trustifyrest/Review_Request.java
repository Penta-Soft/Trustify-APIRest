package com.example.Trustifyrest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Review_Request {
    private String companyAddress;
    private int startRange, endRange;
    private static final int MAXREVIEWS =25;
    public Review_Request(String address, int startRange, int endRange) {
        setCompanyAddress(address);
        setStartRange(startRange);
        setEndRange(endRange);
        checkMaxReviews(startRange, endRange);
    }
    public void checkMaxReviews(int startRange, int endRange) {
        if((endRange - startRange) > MAXREVIEWS) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error: you can't ask for more than 25 review per call");
        }
    }
    public void setCompanyAddress(String companyAddress) {
        if(!org.web3j.crypto.WalletUtils.isValidAddress(companyAddress)){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error: address is not valid");
        }
        this.companyAddress = companyAddress;
    }

    public void setStartRange(int startRange) {
        if(startRange < 0)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error: startRange can not be negative");
        this.startRange = startRange;
    }

    public void setEndRange(int endRange) {
        if(startRange > endRange)
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error: startRange can not be grater that endRange");
        this.endRange = endRange;
    }

    public int getStartRange() {
        return startRange;
    }

    public int getEndRange() {
        return endRange;
    }


    public String getCompanyAddress() {
        return companyAddress;
    }
}

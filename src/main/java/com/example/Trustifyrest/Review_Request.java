package com.example.Trustifyrest;

public class Review_Request {
    private String companyAddress;
    private int startRange, endRange;
    private static final int maxreviews =25;
    public Review_Request(String address, int startRange, int endRange) {
        if(startRange < 0)
            throw new IllegalArgumentException("Error: startRange can not be negative");
        if(startRange > endRange)
            throw new IllegalArgumentException("Error: startRange can not be grater that endRange");
        if(!org.web3j.crypto.WalletUtils.isValidAddress(companyAddress)){
            throw new IllegalArgumentException("Error: not a valid address");
        }
        if((endRange - startRange) > maxreviews){
            throw new IllegalArgumentException("Error: you can't ask for more than 25 review per call");
        }
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public int getStartRange() {
        return startRange;
    }

    public int getEndRange() {
        return endRange;
    }
}

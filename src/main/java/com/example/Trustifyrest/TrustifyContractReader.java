package com.example.Trustifyrest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TrustifyContractReader {
    private String companyAddress;
    private String contractAddress = "0x1cbA4981Ab026266197634a0994033B662f4012e";
    private int startRange, endRange;
    Web3j web3j;

    public TrustifyContractReader(Review_Request request) throws Exception {
        this.companyAddress = request.getCompanyAddress();
        this.startRange = request.getStartRange();
        this.endRange = request.getEndRange();
        web3j = Web3j.build(new HttpService()); // defaults to http://localhost:8545/
    }

    public TrustifyContractReader(String address, int startRange, int endRange) {
        this.companyAddress = address;
        this.startRange = startRange;
        this.endRange = endRange;
        web3j = Web3j.build(new HttpService()); // defaults to http://localhost:8545/
    }

    public List<Review> getReviews() throws Exception {
        List<String> addresses = web3j.ethAccounts().send().getAccounts();
        ReadonlyTransactionManager ReadOnlyManager = new ReadonlyTransactionManager(web3j, addresses.get(0));

        Trustify trustify = Trustify.load(contractAddress, web3j, ReadOnlyManager, new DefaultGasProvider());
        List<Review> reviewList = new ArrayList<>();
        if(!trustify.isValid()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The conntract adress: "+companyAddress+" is not found");
        }
        final Tuple3<List<String>, List<BigInteger>, List<String>> result = trustify.GetNCompanyReview(
                new BigInteger(Integer.toString(startRange)),
                new BigInteger(Integer.toString(endRange)),
                companyAddress).sendAsync().get();


        for (int i = 0; i < result.component1().size(); i++) {
            reviewList.add(new Review(result.component1().get(i), result.component2().get(i).intValue(), result.component3().get(i)));
        }
       return reviewList;



    }

}

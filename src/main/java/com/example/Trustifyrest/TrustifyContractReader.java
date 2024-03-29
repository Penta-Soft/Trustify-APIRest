package com.example.Trustifyrest;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TrustifyContractReader {
    private String companyAddress;
    private static final String CONTRACT_ADDRESS = "0x3361fe8BfD202239d43325a053C7Cac246517a98";
    private static final String DEFAULT_READONLY_ADDRESS = "0xB114A1015d4f5f99750C325c146C7B1429139828";
    private int startRange, endRange;
    private Web3j web3j;

    public TrustifyContractReader(Review_Request request) throws Exception {
        this.companyAddress = request.getCompanyAddress();
        this.startRange = request.getStartRange();
        this.endRange = request.getEndRange();
        web3j = Web3j.build(new HttpService("https://sepolia.infura.io/v3/1caadfe504ce4531b041de4bc8927ceb")); // defaults to http://localhost:8545/
    }



    public List<Review> getReviews() throws Exception {
        //List<String> addresses = web3j.ethAccounts().send().getAccounts();  //USE THIS ONLY FOR LOCAL GANACHE BLOCKCHAIN
        ReadonlyTransactionManager ReadOnlyManager = new ReadonlyTransactionManager(web3j, DEFAULT_READONLY_ADDRESS); //FOR LOCAL BLOCKCHAIN USE new ReadonlyTransactionManager(web3j, addresses.get(0));

        Trustify trustify = Trustify.load(CONTRACT_ADDRESS, web3j, ReadOnlyManager, new DefaultGasProvider());
        List<Review> reviewList = new ArrayList<>();

        Tuple3<List<String>, List<BigInteger>, List<String>> result;

        result= trustify.getCompanyReview(
                new BigInteger(Integer.toString(startRange)),
                new BigInteger(Integer.toString(endRange)),
                companyAddress).sendAsync().get();
            for (int i = 0; i < result.component1().size(); i++) {
                reviewList.add(new Review(result.component1().get(i), result.component2().get(i).intValue(), result.component3().get(i)));}

       return reviewList;



    }

}

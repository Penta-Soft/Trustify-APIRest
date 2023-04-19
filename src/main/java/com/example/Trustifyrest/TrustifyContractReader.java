package com.example.Trustifyrest;

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
    private static final String contractAddress = "0xC3B3Eb5656b404C85FF945317Ca86b9E2dad3a39";
    private int startRange, endRange;
    Web3j web3j;

    public TrustifyContractReader(Review_Request review_request) throws Exception {
        this.companyAddress = review_request.getCompanyAddress();
        this.startRange= review_request.getStartRange();
        this.endRange= review_request.getEndRange();
        web3j = Web3j.build(new HttpService());
    }

    public List<Review> getReviews() throws Exception {
        List<String> addresses = web3j.ethAccounts().send().getAccounts();
        ReadonlyTransactionManager ReadOnlyManager = new ReadonlyTransactionManager(web3j, addresses.get(0));

        Trustify trustify = Trustify.load(contractAddress, web3j, ReadOnlyManager, new DefaultGasProvider());
        final Tuple3<List<String>, List<BigInteger>, List<String>> result = trustify.GetNCompanyReview(
                new BigInteger(Integer.toString(startRange)),
                new BigInteger(Integer.toString(endRange)),
                companyAddress).sendAsync().get();

        List<Review> reviewList = new ArrayList<>();
        for (int i = 0; i < result.component1().size(); i++) {
            reviewList.add(new Review(result.component1().get(i), result.component2().get(i).intValue(), result.component3().get(i)));
        }

        return reviewList;
    }

}

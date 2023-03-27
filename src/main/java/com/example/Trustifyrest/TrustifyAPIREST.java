package com.example.Trustifyrest;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TrustifyAPIREST {
    private String companyAddress;
    private String contractAddress = "0x6e6C3db807eE8f981bEF9F644626e7eccB72Ae59";
    private int startRange, endRange;
    private Web3j web3j;

    public TrustifyAPIREST(String companyAddress, int startRange, int endRange) throws Exception {
        this.companyAddress = companyAddress;
        this.startRange = startRange;
        this.endRange = endRange;
        web3j = Web3j.build(new HttpService()); // defaults to http://localhost:8545/
    }

    public List<Review> getReviews() throws Exception {
        List<String> addresses = web3j.ethAccounts().send().getAccounts();
        ReadonlyTransactionManager ReadOnlyManager = new ReadonlyTransactionManager(web3j, addresses.get(0));

        Trustify trustify = Trustify.load(contractAddress, web3j, ReadOnlyManager, new DefaultGasProvider());
        final Tuple2<List<String>, List<BigInteger>> result = trustify.GetNCompanyReview(
                new BigInteger(Integer.toString(startRange)),
                new BigInteger(Integer.toString(endRange)),
                companyAddress).sendAsync().get();

        List<Review> reviewList = new ArrayList<>();
        for (int i = 0; i < result.component1().size(); i++) {
            reviewList.add(new Review(result.component1().get(i), result.component2().get(i).intValue()));
        }

        return reviewList;
    }

}

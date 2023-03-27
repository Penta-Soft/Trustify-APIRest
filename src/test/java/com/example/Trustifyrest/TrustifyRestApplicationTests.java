package com.example.Trustifyrest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Pair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.Tuple;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrustifyRestApplicationTests {
	final Web3j web3j = Web3j.build(new HttpService()); // defaults to http://localhost:8545/

	/*
	@Test
	void DeployContracts() throws Exception {
		//final ReadonlyTransactionManager manager = new ReadonlyTransactionManager(web3j, address);
		List<String> addresses = web3j.ethAccounts().send().getAccounts();

		ClientTransactionManager client = new ClientTransactionManager(web3j, addresses.get(0));
		TCoin tCoin = TCoin.deploy(web3j, client, new DefaultGasProvider()).send();

		tCoin.drip().sendAsync().get();
		final BigDecimal value = Convert.fromWei(String.valueOf(tCoin.balanceOf(web3j.ethAccounts().send().getAccounts().get(0)).send()), Convert.Unit.ETHER);
		assertEquals("100000", value.toString());
	}
*/

	@Test
	void Read5ReviewsFromAnAddress() throws Exception {
		final String contractAddress = "0xE8231d7DC46B5F0fA3CCA83300D1084252853178";
		final String companyAddress = "0x7b3F2EFc8e470F7eE6DE70135655921Ee81BB9e7";

		//final Web3j web3j = Web3j.build(new HttpService()); // defaults to http://localhost:8545/
		//List<String> addresses = web3j.ethAccounts().send().getAccounts();

		//ClientTransactionManager client = new ClientTransactionManager(web3j, addresses.get(0));
		//ClientTransactionManager client2 = new ClientTransactionManager(web3j, addresses.get(1));
		//ClientTransactionManager client3 = new ClientTransactionManager(web3j, addresses.get(2));
		//ClientTransactionManager client4 = new ClientTransactionManager(web3j, addresses.get(3));
		//ReadonlyTransactionManager ReadOnlyManager = new ReadonlyTransactionManager(web3j, addresses.get(0));

		//Trustify trustify = Trustify.load(contractAddress, web3j, ReadOnlyManager, new DefaultGasProvider());
		//final Tuple2<List<String>, List<BigInteger>> result = trustify.GetNCompanyReview(new BigInteger("1"), new BigInteger("4"), companyAddress).sendAsync().get();

		//System.out.println(result.component1().get(0));
		List<String> addresses = web3j.ethAccounts().send().getAccounts();
		ReadonlyTransactionManager ReadOnlyManager = new ReadonlyTransactionManager(web3j, addresses.get(0));

		Trustify trustify = Trustify.load(contractAddress, web3j, ReadOnlyManager, new DefaultGasProvider());
		final Tuple2<List<String>, List<BigInteger>> result = trustify.GetNCompanyReview(
				new BigInteger(Integer.toString(1)),
				new BigInteger(Integer.toString(1)),
				companyAddress).sendAsync().get();

		Review review = new Review(result.component1().get(0), result.component2().get(0).intValue());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValueAsString(review);

		System.out.println(objectMapper.writeValueAsString(review));


	}

}

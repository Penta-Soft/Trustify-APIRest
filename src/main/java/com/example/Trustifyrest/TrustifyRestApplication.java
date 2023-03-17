package com.example.Trustifyrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

@SpringBootApplication
public class TrustifyRestApplication {

	public static void main(String[] args) throws Exception {

		//SpringApplication.run(TrustifyRestApplication.class, args);

		final Web3j web3j = Web3j.build(new HttpService()); // defaults to http://localhost:8545/
		//final String contractAddress = "0xFa0a774ad1c0dce1178D2f5320eE157a307Dae56";
		//final String address = "0x429B104562dEE54B1324625a8845Bd7Ca1B3eD3D";
		//final ReadonlyTransactionManager manager = new ReadonlyTransactionManager(web3j, address);

		//final String walletKey = "0x7e1b4b7697effe012906ee41b2e942cca34848b1c4ebe6f1fdfd215ad3095e57";
		//final String customerAccount = web3j.ethAccounts().send().getAccounts().get(0);
		final String customerAccountCredential = "0x7e1b4b7697effe012906ee41b2e942cca34848b1c4ebe6f1fdfd215ad3095e57";
		final DefaultGasProvider contractGasProvider = new DefaultGasProvider();
		TullioCoin tullioCoin = TullioCoin.deploy(web3j, Credentials.create(customerAccountCredential), new DefaultGasProvider()).send();
		ReviewHolder reviewHolder = ReviewHolder.deploy(web3j, Credentials.create(customerAccountCredential), new DefaultGasProvider(), tullioCoin.getContractAddress()).send();

		tullioCoin.drip().sendAsync().get();
		//reviewHolder.
	}

}

package com.example.Trustifyrest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@WebMvcTest(SmartContractReaderController.class)
class TrustifyRestApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void Insert_a_non_valid_address() throws Exception {
		final String companyAddress = "/reviews?address=0x20DcB8C5C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=0&endRange=4";
		RequestBuilder request = MockMvcRequestBuilders.get(companyAddress);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("{\"errorMessage\":\"Error: not a valid address\"}",result.getResponse().getContentAsString());
	}

	@Test
	void Insert_a_non_valid_startRange() throws Exception{
		final String companyAddress = "/reviews?address=0x20DcB8C5c4C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=-1&endRange=4";
		RequestBuilder request = MockMvcRequestBuilders.get(companyAddress);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("{\"errorMessage\":\"Error: startRange can not be negative\"}",result.getResponse().getContentAsString());
	}

	@Test
	void Insert_startRanege_grater_than_endRange() throws Exception{
		final String companyAddress = "/reviews?address=0x20DcB8C5c4C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=5&endRange=4";
		RequestBuilder request = MockMvcRequestBuilders.get(companyAddress);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("{\"errorMessage\":\"Error: startRange can not be grater that endRange\"}",result.getResponse().getContentAsString());
	}

}

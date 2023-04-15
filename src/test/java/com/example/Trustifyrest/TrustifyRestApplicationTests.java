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
		final String companyAddress = "/reviews?address=0x20DcB8C5c4C4891DeF4B3f0D8BC2C3EE3595D58&startRange=0&endRange=4";
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

	@Test
	void Call_for_5_review () throws Exception{
		final String companyAddress = "/reviews?address=0x20DcB8C5c4C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=0&endRange=4";
		RequestBuilder request = MockMvcRequestBuilders.get(companyAddress);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("[{\"text\":\"prova prova SASASASASASASASA\",\"stars\":3,\"state\":\"ACTIVE\"},{\"text\":\"prova prova SASASASASASASA\",\"stars\":2,\"state\":\"ACTIVE\"},{\"text\":\"prova prova SASASASASASA\",\"stars\":1,\"state\":\"ACTIVE\"},{\"text\":\"prova prova SASASASASA\",\"stars\":5,\"state\":\"ACTIVE\"},{\"text\":\"prova prova SASASASA\",\"stars\":4,\"state\":\"ACTIVE\"}]",result.getResponse().getContentAsString());
	}

	@Test
	void Call_for_a_company_without_any_reviews () throws Exception{
		final String companyAddress = "/reviews?address=0x20DcB1C5c4C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=0&endRange=4";
		RequestBuilder request = MockMvcRequestBuilders.get(companyAddress);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("{\"errorMessage\":\"org.web3j.tx.exceptions.ContractCallException: Contract Call has been reverted by the EVM with the reason: 'VM Exception while processing transaction: revert This company have not received any reviews'.\"}",result.getResponse().getContentAsString());
	}

	@Test
	void Ask_for_more_than_25_review_per_call() throws Exception{
		final String companyAddress = "/reviews?address=0x20DcB8C5c4C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=0&endRange=30";
		RequestBuilder request = MockMvcRequestBuilders.get(companyAddress);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("{\"errorMessage\":\"Error: you can't ask for more than 25 review per call\"}",result.getResponse().getContentAsString());
	}

}

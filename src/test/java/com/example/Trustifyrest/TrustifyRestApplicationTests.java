package com.example.Trustifyrest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(SmartContractController.class)
class TrustifyRestApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void Insert_a_non_valid_address() throws Exception {
		final String endpoint = "/reviews?address=0x20DcB8C5c4C4891DeF4B3f0D8BC2C3EE3595D58&startRange=0&endRange=4";
		RequestBuilder request = MockMvcRequestBuilders.get(endpoint);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("400 BAD_REQUEST \"Error: address is not valid\"",result.getResponse().getContentAsString());
	}

	@Test
	void Ask_for_a_non_existing_endpoint() throws Exception{
		final String endpoint = "/wrongEndpoint";
		RequestBuilder request = MockMvcRequestBuilders.get(endpoint);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("404 NOT_FOUND \"Error: the requested resource was not found\"",result.getResponse().getContentAsString());
	}

	@Test
	void Insert_a_non_valid_startRange() throws Exception{
		final String endpoint = "/reviews?address=0x20DcB8C5c4C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=-1&endRange=4";
		RequestBuilder request = MockMvcRequestBuilders.get(endpoint);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("400 BAD_REQUEST \"Error: startRange can not be negative\"",result.getResponse().getContentAsString());
	}

	@Test
	void Insert_startRanege_grater_than_endRange() throws Exception{
		final String endpoint = "/reviews?address=0x20DcB8C5c4C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=5&endRange=4";
		RequestBuilder request = MockMvcRequestBuilders.get(endpoint);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("400 BAD_REQUEST \"Error: startRange can not be grater that endRange\"",result.getResponse().getContentAsString());
	}


	@Test
	void Ask_for_a_review () throws Exception{
		final String companyAddress = "/reviews?address=0xB114A1015d4f5f99750C325c146C7B1429139828&startRange=0&endRange=1";
		RequestBuilder request = MockMvcRequestBuilders.get(companyAddress);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("[{\"text\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed suscipit massa. In non consequat nisi, sit amet ornare orci.\",\"stars\":5,\"state\":\"DELETED\"}]",result.getResponse().getContentAsString());
	}


	@Test
	void Call_for_a_company_without_any_reviews () throws Exception{
		final String endpoint = "/reviews?address=0x20DcB1C5c4C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=0&endRange=4";
		RequestBuilder request = MockMvcRequestBuilders.get(endpoint);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("400 BAD_REQUEST \"Error: This company have not received any reviews yet\"",result.getResponse().getContentAsString());
	}

	@Test
	void Ask_for_more_than_25_review_per_call() throws Exception{
		final String endpoint = "/reviews?address=0x20DcB8C5c4C4891DeF4B3f0D8BC2C3EEE3595D58&startRange=0&endRange=30";
		RequestBuilder request = MockMvcRequestBuilders.get(endpoint);
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("400 BAD_REQUEST \"Error: you can't ask for more than 25 review per call\"",result.getResponse().getContentAsString());
	}

}

package com.service.controller;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.service.service.DiscountServiceService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(DiscountServiceController.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiscountServiceControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DiscountServiceService discountService;

	@Test
	public void A_verifyInput1() throws Exception {
		Mockito.when(discountService.calculateDiscount(Mockito.anyString(), Mockito.anyInt())).thenReturn(1045);
		
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/discount/Standard/1050").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals("1045", mvcResult.getResponse().getContentAsString());
	}
}

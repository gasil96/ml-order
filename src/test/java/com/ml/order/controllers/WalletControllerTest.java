package com.ml.order.controllers;

import com.ml.order.dtos.WalletDTO;
import com.ml.order.services.WalletService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class WalletControllerTest {

	private final Page<WalletDTO> WALLET_DTO_PAGE = builderWalletDTOList();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webAppContext;

	@MockBean
	private WalletService walletService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = webAppContextSetup(webAppContext).build();
	}

	@Test
	public void listAllWithSuccess() throws Exception {
		when(walletService.listAll(any())).thenReturn(WALLET_DTO_PAGE);

		mockMvc.perform(get("/v1/wallets/list")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.[0].id").value(WALLET_DTO_PAGE.getContent().get(0).getId()))
				.andExpect(status().isOk());
	}

	private Page<WalletDTO> builderWalletDTOList() {
		return new PageImpl<>(Collections.singletonList(
				WalletDTO.builder()
						.id(1L)
						.build()
		));
	}

}

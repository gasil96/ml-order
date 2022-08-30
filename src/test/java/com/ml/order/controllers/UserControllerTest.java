package com.ml.order.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.order.dtos.UserDTO;
import com.ml.order.dtos.WalletDTO;
import com.ml.order.services.UserService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

	private static final String EMAIL = "fioringo.bordiga@mailinator.com";
	private static final String NAME = "Fiorindo Bordiga";
	private final Page<UserDTO> USER_DTO_PAGE = builderUserDTOList();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webAppContext;

	@MockBean
	private UserService userService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = webAppContextSetup(webAppContext).build();
	}

	@Test
	public void createWithSuccess() throws Exception {
		mockMvc.perform(post("/v1/users/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(UserDTO.builder()
								.email(EMAIL)
								.name(NAME)
								.wallet(WalletDTO.builder().build())
								.build())))
				.andExpect(status().isCreated());

		verify(userService).create(any());
	}

	@Test
	public void createWithoutRequiredAttribute() throws Exception {
		mockMvc.perform(post("/v1/users/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(UserDTO.builder()
								.name(NAME)
								.wallet(WalletDTO.builder().build())
								.build())))
				.andExpect(status().isBadRequest());

		verify(userService, times(0)).create(any());
	}

	@Test
	public void updateWithSuccess() throws Exception {
		mockMvc.perform(put("/v1/users/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(UserDTO.builder()
								.email(EMAIL)
								.name(NAME)
								.wallet(WalletDTO.builder().build())
								.build())))
				.andExpect(status().isNoContent());

		verify(userService).update(any());
	}

	@Test
	public void deleteWithSuccess() throws Exception {
		mockMvc.perform(delete("/v1/users/delete")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(UserDTO.builder()
								.email(EMAIL)
								.name(NAME)
								.wallet(WalletDTO.builder().build())
								.build())))
				.andExpect(status().isNoContent());

		verify(userService).delete(any());
	}

	@Test
	public void listAllWithSuccess() throws Exception {
		when(userService.listAll(any())).thenReturn(USER_DTO_PAGE);

		mockMvc.perform(get("/v1/users/list")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.[0].id").value(USER_DTO_PAGE.getContent().get(0).getId()))
				.andExpect(status().isOk());
	}

	private Page<UserDTO> builderUserDTOList() {
		return new PageImpl<>(Collections.singletonList(
				UserDTO.builder()
						.id(1L)
						.build()
		));
	}

}

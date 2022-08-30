package com.ml.order.services;

import com.ml.order.dtos.UserDTO;
import com.ml.order.dtos.WalletDTO;
import com.ml.order.entities.User;
import com.ml.order.entities.Wallet;
import com.ml.order.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

	private static final Long ID = 1L;
	private static final BigDecimal BALANCE = BigDecimal.TEN;
	private static final String EMAIL = "teste@mailinator.com";
	private static final Pageable PAGEABLE = PageRequest.of(0, 1);
	private static final String NAME = "Fiorindo Bodiga";
	private final UserDTO userDTOMock = builderUserDTO();
	private final User userMock = builderUser();

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private ModelMapper mapper;

	@Mock
	private UserRepository userRepository;

	@Test
	public void createWithSuccess() {
		when(mapper.map(userDTOMock, User.class)).thenReturn(userMock);

		userService.create(userDTOMock);

		verify(userRepository).save(userMock);
	}

	@Test
	public void updateWithSuccess() {
		when(mapper.map(userDTOMock, User.class)).thenReturn(userMock);

		userService.update(userDTOMock);

		verify(userRepository).save(userMock);
	}

	@Test
	public void deleteWithSuccess() {
		when(mapper.map(userDTOMock, User.class)).thenReturn(userMock);

		userService.delete(userDTOMock);

		verify(userRepository).delete(userMock);
	}

	@Test
	public void listAll() {
		when(userRepository.findAll(PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(userMock)));

		Page<UserDTO> userDTOPage = userService.listAll(PAGEABLE);

		assertEquals(1, userDTOPage.getSize());
	}

	private WalletDTO builderWalletDTO() {
		return WalletDTO.builder()
				.id(ID)
				.balance(BALANCE)
				.user(UserDTO.builder().email(EMAIL).build())
				.build();
	}

	private Wallet builderWallet() {
		return Wallet.builder()
				.id(ID)
				.user(User.builder().email(EMAIL).build())
				.balance(BALANCE)
				.build();
	}

	private UserDTO builderUserDTO() {
		return UserDTO.builder()
				.email(EMAIL)
				.name(NAME)
				.wallet(builderWalletDTO())
				.build();
	}

	private User builderUser() {
		return User.builder()
				.email(EMAIL)
				.name(NAME)
				.wallet(builderWallet())
				.build();
	}

}

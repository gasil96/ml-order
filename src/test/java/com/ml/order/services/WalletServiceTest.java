package com.ml.order.services;

import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.UserDTO;
import com.ml.order.dtos.WalletDTO;
import com.ml.order.entities.User;
import com.ml.order.entities.Wallet;
import com.ml.order.exceptions.BusinessException;
import com.ml.order.repositories.WalletRepository;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class WalletServiceTest {

	private static final Long ID = 1L;
	private static final BigDecimal BALANCE = BigDecimal.TEN;
	private static final String EMAIL = "teste@mailinator.com";
	private static final Pageable PAGEABLE = PageRequest.of(0, 1);
	private static final BigDecimal PRICE = BigDecimal.TEN;
	private static final Long QUANTITY = 100L;
	private final WalletDTO walletDTOMock = builderWalletDTO();
	private final Wallet walletMock = builderWallet();

	@InjectMocks
	private WalletServiceImpl walletService;

	@Mock
	private ModelMapper mapper;

	@Mock
	private WalletRepository walletRepository;

	@Test
	public void createWithSuccess() {
		when(mapper.map(walletDTOMock, Wallet.class)).thenReturn(walletMock);

		walletService.create(walletDTOMock);

		verify(walletRepository).save(walletMock);
	}

	@Test
	public void listAllWithSuccess() {
		when(walletRepository.findAll(PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(walletMock)));
		Page<WalletDTO> walletDTOPage = walletService.listAll(PAGEABLE);

		assertEquals(1, walletDTOPage.getSize());
	}

	@Test
	public void orderFinishedWithSuccess() {
		when(walletRepository.findById(ID)).thenReturn(Optional.of(walletMock));

		walletService.orderFinished(TypeOrder.SELL, PRICE, QUANTITY, ID);

		verify(walletRepository).save(walletMock);
	}

	@Test(expected = BusinessException.class)
	public void orderFinishedWithBusinessException() {
		when(walletRepository.findById(ID)).thenReturn(Optional.empty());

		walletService.orderFinished(TypeOrder.SELL, PRICE, QUANTITY, ID);
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

}

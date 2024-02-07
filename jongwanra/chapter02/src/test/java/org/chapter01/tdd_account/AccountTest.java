package org.chapter01.tdd_account;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 */
class AccountTest {
	private Account account;

	@Test
	public void testAccount() {
		account = new Account();

		assertThat(account).isNotNull();
	}

	@DisplayName("10_000원으로 계좌 생성 -> 잔고 조회 결과 일치")
	@Test
	void testGetBalance() {
		account = new Account(10_000);
		assertThat(account.getBalance()).isEqualTo(10_000);

	}

	@DisplayName("1_000원으로 계좌 생성 -> 잔고 조회 결과 일치")
	@Test
	void testGetBalance2() {
		account = new Account(1_000);
		assertThat(account.getBalance()).isEqualTo(1_000);

	}

	@DisplayName("0원으로 계좌 생성 -> 잔고 조회 결과 일치")
	@Test
	void testGetBalance3() {
		account = new Account(0);
		assertThat(account.getBalance()).isEqualTo(0);

	}

	@Test
	public void testDeposit() {
		Account account = new Account(10_000);
		account.deposit(1_000);
		assertThat(account.getBalance()).isEqualTo(11_000);
	}

	@Test
	public void testWithdraw() {
		Account account = new Account(10_000);
		account.withdraw(1_000);

		assertThat(account.getBalance()).isEqualTo(9_000);
	}
}

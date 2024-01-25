package org.chapter01.learning;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {
	private Calculator calculator;


	@BeforeEach
	public void setUp() {
		System.out.println(":: before ::");
		calculator = new Calculator();
	}

	@AfterEach
	void tearDown() {
		System.out.println(":: after ::");
	}

	@DisplayName("3과 4를 더한다.")
	@Test
	void add() {
		// when & then
		System.out.println(":: add ::");
		assertThat(7).isEqualTo(calculator.add(3,4));
	}

	@DisplayName("3에서 4를 뺀다.")
	@Test
	void subtract() {
		// given
		System.out.println(":: substract ::");
		// when & then
		assertThat(-1).isEqualTo(calculator.subtract(3, 4));
	}

	@DisplayName("3과 4를 곱한다.")
	@Test
	void multiply() {
		// when & then
		System.out.println(":: multiply ::");
		assertThat(12).isEqualTo(calculator.multiply(3, 4));
	}

	@DisplayName("3에서 4를 나눈다.")
	@Test
	void divide() {
		// when & then
		System.out.println(":: divide ::");
		assertThat(0).isEqualTo(calculator.divide(3, 4));
	}

}

package org.chapter01.tdd_string_calculator;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringCalculatorTest {

	@DisplayName("빈 문자열, null, whiteSpace만 존재하는 문자열이 주어질 경우 0을 반환한다.")
	@Test
	void add() {
		StringCalculator stringCalculator = new StringCalculator();
		assertThat(stringCalculator.add("")).isEqualTo(0);
		assertThat(stringCalculator.add(null)).isEqualTo(0);
		assertThat(stringCalculator.add("     ")).isEqualTo(0);
	}

	@DisplayName("','를 구분자로 가지는 숫자 여러개를 더한다.")
	@Test
	void add2() {
		// given
		StringCalculator stringCalculator = new StringCalculator();

		// when & then
		assertThat(stringCalculator.add("1,2")).isEqualTo(3);
		assertThat(stringCalculator.add("1,3")).isEqualTo(4);
		assertThat(stringCalculator.add("1,3,5")).isEqualTo(9);
	}

	@DisplayName("':'를 구분자로 가지는 숫자 여러개를 더한다.")
	@Test
	void add3() {
		// given
		StringCalculator stringCalculator = new StringCalculator();

		// when & then
		assertThat(stringCalculator.add("1:2")).isEqualTo(3);
		assertThat(stringCalculator.add("1:3")).isEqualTo(4);
		assertThat(stringCalculator.add("1:3:5")).isEqualTo(9);
	}


	@DisplayName("':'와 ','를 혼합 구분자로 가지는 숫자 여러개를 더한다.")
	@Test
	void add4() {
		// given
		StringCalculator stringCalculator = new StringCalculator();

		// when & then
		assertThat(stringCalculator.add("1:3,5")).isEqualTo(9);
		assertThat(stringCalculator.add("1,3:5")).isEqualTo(9);
	}

	@DisplayName("커스텀 구분자를 가지는 숫자 여러개를 더한다.")
	@Test
	void add5() {
		// given
		StringCalculator stringCalculator = new StringCalculator();

		// when & then
		assertThat(stringCalculator.add("//a\n1a2a3a")).isEqualTo(6);
		assertThat(stringCalculator.add("//!\n1!3!5")).isEqualTo(9);
	}


	@DisplayName("문자열에 음수가 들어간 경우 RuntimeException을 발생시킨다.")
	@Test
	void add6() {
		StringCalculator stringCalculator = new StringCalculator();

		assertThatThrownBy(() -> stringCalculator.add("-1:2:3"))
			.isInstanceOf(RuntimeException.class);

		assertThatThrownBy(() -> stringCalculator.add("1:-2:3"))
			.isInstanceOf(RuntimeException.class);

		assertThatThrownBy(() -> stringCalculator.add("1:2:-3"))
			.isInstanceOf(RuntimeException.class);

	}


}

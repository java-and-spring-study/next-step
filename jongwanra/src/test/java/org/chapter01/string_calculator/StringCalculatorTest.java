package org.chapter01.string_calculator;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringCalculatorTest {
	private StringCalculator stringCalculator;

	@BeforeEach
	void setUp() {
		stringCalculator = new StringCalculator();
	}


	@DisplayName("쉼표(,)를 구분자로 가지는 문자열을 전달하는 경우 쉼표를 기준으로 분리한 각 숫자의 합을 반환한다.")
	@Test
	void add() {
		// given
		final String targetString1 = "1,2";
		final String targetString2 = "1,2,3,4";

		// when & then
		assertThat(3).isEqualTo(stringCalculator.add(targetString1));
		assertThat(10).isEqualTo(stringCalculator.add(targetString2));
	}


	@DisplayName("콜론(:)을 구분자로 가지는 문자열을 전달하는 경우 콜론을 기준으로 분리한 각 숫자의 합을 반환한다.")
	@Test
	void add1() {
		// given
		final String targetString1 = "1:2";
		final String targetString2 = "1:2:3:4";

		// when & then
		assertThat(3).isEqualTo(stringCalculator.add(targetString1));
		assertThat(10).isEqualTo(stringCalculator.add(targetString2));
	}

	@DisplayName("커스텀 구분자(문자열 가장 앞 부분에 '//' 와 '\n' 사이의 문자)로 가지는 문자열을 전달하는 경우 커스텀 구분자를 기준으로 분리한 각 숫자의 합을 반환한다.")
	@Test
	void add2() {
		// given
		final String targetString1 = "//;\n1;2;3";
		final String targetString2 = "//q\n1q2q3q4q";

		// when & then
		assertThat(6).isEqualTo(stringCalculator.add(targetString1));
		assertThat(10).isEqualTo(stringCalculator.add(targetString2));
	}

	@DisplayName("문자열 계산기에 음수(-)를 전달하는 경우 RuntimeException을 발생시킨다.")
	@Test
	void add3() {
		// given
		final String targetString1 = "1:2:-3";
		final String targetString2 = "-1:2:3";
		final String targetString3 = "1:-2:3";

		// when & then
		assertThatThrownBy(() -> stringCalculator.add(targetString1))
			.isInstanceOf(RuntimeException.class);

		assertThatThrownBy(() -> stringCalculator.add(targetString2))
			.isInstanceOf(RuntimeException.class);

		assertThatThrownBy(() -> stringCalculator.add(targetString3))
			.isInstanceOf(RuntimeException.class);
	}
}

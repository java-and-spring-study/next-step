package org.nextstep.chap02;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringCalculatorTest {

	private StringCalculator stringCalculator;

	@BeforeEach
	void setUp() {
		stringCalculator = new StringCalculator();
	}

	@Test
	void 음수를_전달하면_예외를_발생한다(){
		String str = "-1,2,3";
		assertThrows(RuntimeException.class,
			()-> stringCalculator.calculate(str));
	}

	@Test
	void empty거나_null을_입력하면_0을_반환해라(){
		int result = stringCalculator.calculate("");
		assertThat(result).isZero();

		result = stringCalculator.calculate(null);
		assertThat(result).isZero();
	}

	@Test
	void 숫자를_하나만_입력하는_경우_해당_숫자를_반환한다(){
		int result = stringCalculator.calculate("6");
		assertThat(result).isEqualTo(6);
	}

	@Test
	void 쉼표_또는_콜론으로_된_문자열을_계산한다(){
		String str = "1,2,3";
		int result = stringCalculator.calculate(str);
		assertThat(result).isEqualTo(6);
	}

}

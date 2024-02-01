package org.chapter01.tdd_string_calculator;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SubStringTest {

	@DisplayName("String의 메서드인 subString()을 테스트한다.")
	@Test
	void subString() {
		assertThat("abcdefg".substring(0)).isEqualTo("abcdefg");
		assertThat("abcdefg".substring(1)).isEqualTo("bcdefg");
		assertThat("//a\n1a2a3a".substring(4)).isEqualTo("1a2a3a");
		assertThat("//a\n1a2a3a".substring(0)).isEqualTo("//a\n1a2a3a");
		assertThat("//a\n1a2a3a".substring(1)).isEqualTo("/a\n1a2a3a");
		assertThat("//a\n1a2a3a".substring(2)).isEqualTo("a\n1a2a3a");
		assertThat("//a\n1a2a3a".substring(3)).isEqualTo("\n1a2a3a");
		assertThat("//a\n1a2a3a".substring(4)).isEqualTo("1a2a3a");
		assertThat("//a\n1a2a3a".substring(5)).isEqualTo("a2a3a");
	}

	@DisplayName("'\n'은 하나의 문자이다.")
	@Test
	void character() {
		assertThat("\n".length()).isOne();
	}
}

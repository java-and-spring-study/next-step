package org.chapter01.tdd_string_calculator;

import java.util.Arrays;

public class StringCalculator {
	public int add(String value) {
		if(isBlank(value)) {
			return 0;
		}

		return sum(toPositiveNumbers(split(value)));
	}

	private  boolean isBlank(String value) {
		return value == null || value.isBlank();
	}

	private String[] split(String value) {
		String defaultSeperator = ":|,";
		if(value.startsWith("//")) {
			final String seperator = String.valueOf(value.charAt(2));
		 	return value.substring(4).split(seperator);
		}

		return value.split(defaultSeperator);
	}

	private Integer[] toPositiveNumbers(String[] values) {
		Integer[] array = new Integer[values.length];
		for(int index = 0; index < values.length; index++) {
			int number = Integer.parseInt(values[index]);
			validateIsPositive(number);
			array[index] = number;
		}
		return array;
	}

	private void validateIsPositive(int number) {
		if(number < 0) {
			throw new RuntimeException();
		}
	}

	private int sum(Integer[] numbers) {
		return Arrays.stream(numbers)
			.mapToInt(Integer::intValue)
			.sum();
	}
}

/**
 *
 * stream 왜 느릴까?!
 * assertThrow랑 차이점?
 *
 * 시간 측정
 * https://www.baeldung.com/java-override-system-time
 * https://jojoldu.tistory.com/676
 * 자바와 JUnit을 활용한 실용주의 단위 테스트
 */

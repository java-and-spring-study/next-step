package org.chapter01.string_calculator;

import java.util.Arrays;
import java.util.List;

public class StringCalculator {
	private static final String DEFAULT_SEPERATOR = ",|:";
	public int add(final String value) {
		if(isBlank(value)){
			return 0;
		}

		String seperator = findSeperator(value);
		List<Integer> numbers = toInts(split(seperator, removeCustomSeperator(value, seperator)));
		validateHasNegativeNumber(numbers);
		return sum(numbers);
	}

	private List<Integer> toInts(List<String> values) {
		return values.stream()
			.map(Integer::parseInt)
			.toList();
	}

	private  boolean isBlank(String targetValue) {
		return targetValue == null || targetValue.isEmpty();
	}

	private  List<String> split(String seperator, String value) {
		return Arrays.stream(value.split(seperator)).toList();
	}

	private String removeCustomSeperator(String value, String seperator) {
		if(seperator.equals(DEFAULT_SEPERATOR)){
			return value;
		}
		return value.substring(4);
	}

	private String findSeperator(String targetString) {
		if(targetString.startsWith("//")){
			return String.valueOf(targetString.charAt(2));
		}
		return DEFAULT_SEPERATOR;

	}

	private int sum(List<Integer> numbers) {
		return numbers.stream()
			.mapToInt(Integer::intValue)
			.sum();
	}


	private void validateHasNegativeNumber(List<Integer> numbers) {
		for (Integer number : numbers) {
			validateIsNegativeNumber(number);
		}
	}

	private void validateIsNegativeNumber(final int number) {
		if(number < 0) {
			throw new RuntimeException("");
		}
	}
}

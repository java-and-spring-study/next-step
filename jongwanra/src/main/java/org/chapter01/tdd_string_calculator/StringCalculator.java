package org.chapter01.tdd_string_calculator;

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
		int sum = 0;
		for (int number : numbers) {
			sum += number;
		}
		return sum;
	}
}

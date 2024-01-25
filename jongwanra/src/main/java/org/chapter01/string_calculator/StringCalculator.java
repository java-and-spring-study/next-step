package org.chapter01.string_calculator;

import java.util.Arrays;
import java.util.List;

public class StringCalculator {
	public int add(final String targetString) {
		// 구분자를 찾자.
		Character seperator = findSeperator(targetString);
		final String pureTargetString = removeCustomSeperatorIfExist(targetString, seperator);
		List<String> splitTargetString = splitBySeperator(seperator, pureTargetString);
		return add(splitTargetString);
	}

	private  List<String> splitBySeperator(Character seperator, String pureTargetString) {
		return Arrays.stream(pureTargetString.split(String.valueOf(seperator))).toList();
	}

	private String removeCustomSeperatorIfExist(String targetString, Character seperator) {
		if(seperator.equals(':') || seperator.equals(',')){
			return targetString;
		}
		return targetString.substring(4);
	}

	private Character findSeperator(String targetString) {
		if(targetString.startsWith("//")){
			return targetString.charAt(2);
		}
		if(targetString.contains(":")){
			return ':';
		}

		return ',';
	}

	private int add(List<String> splitTargetString) {
		int sum = 0;
		for (String s : splitTargetString) {
			final int targetNumber = Integer.parseInt(s);
			validateIsNegativeNumber(targetNumber);
			sum += targetNumber;
		}
		return sum;
	}

	private void validateIsNegativeNumber(final int targetNumber) {
		if(targetNumber < 0) {
			throw new RuntimeException("");
		}
	}
}

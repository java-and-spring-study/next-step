package org.chapter01.string_calculator;

import java.util.Arrays;
import java.util.List;

public class StringCalculator {
	private static final String DEFAULT_SEPERATOR = ",|:";
	public int add(final String targetString) {
		if(targetString == null || targetString.isEmpty()){
			return 0;
		}

		String seperator = findSeperator(targetString);
		final String pureTargetString = removeCustomSeperatorIfExist(targetString, seperator);
		List<String> splitTargetString = splitBySeperator(seperator, pureTargetString);
		validateHasNegativeNumber(splitTargetString);
		return add(splitTargetString);
	}

	private  List<String> splitBySeperator(String seperator, String pureTargetString) {
		return Arrays.stream(pureTargetString.split(seperator)).toList();
	}

	private String removeCustomSeperatorIfExist(String targetString, String seperator) {
		if(seperator.equals(DEFAULT_SEPERATOR)){
			return targetString;
		}
		return targetString.substring(4);
	}

	private String findSeperator(String targetString) {
		if(targetString.startsWith("//")){
			return String.valueOf(targetString.charAt(2));
		}
		return DEFAULT_SEPERATOR;

	}

	private int add(List<String> splitTargetString) {
		return splitTargetString.stream()
			.mapToInt(Integer::parseInt)
			.sum();
	}


	private void validateHasNegativeNumber(List<String> splitTargetString) {
		for (String s : splitTargetString) {
			validateIsNegativeNumber(Integer.parseInt(s));
		}
	}

	private void validateIsNegativeNumber(final int targetNumber) {
		if(targetNumber < 0) {
			throw new RuntimeException("");
		}
	}
}

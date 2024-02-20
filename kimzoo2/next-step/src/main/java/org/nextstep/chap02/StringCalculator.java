package org.nextstep.chap02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	public int calculate(String str) {
		if(str == null || str.isEmpty()){
			return 0;
		}

		return sum(splitString(str));
	}

	private int sum(String[] values){
		int sum = 0;
		int[] intValues = toInt(values);
		for (int intValue : intValues) {
			if(intValue < 0)
				throw new RuntimeException("음수는 허용할 수 없습니다.");
			sum += intValue;
		}
		return sum;
	}

	// 학습 과정 중 과한 리팩토링을 두려워하지 말자.
	private int[] toInt(String[] values){
		int len = values.length;
		int[] intArr = new int[len];
		for (int i = 0; i < len; i++) {
			intArr[i] = Integer.parseInt(values[i]);
		}
		return intArr;
	}

	private String[] splitString(String str){

		Matcher m = Pattern.compile("//(.)\n(.*)").matcher(str);
		if(m.find()){
			String customeDelimeter = m.group(1);
			return m.group(2).split(customeDelimeter);
		}
		return str.split(";|,");
	}
}

package org.nextstep.chap02;

public class CalculatorTest {
	public static void main(String[] args) {
		Calculator cal = new Calculator();
		divide(cal);
		multiply(cal);
		subtract(cal);
		add(cal);
	}

	private static void divide(Calculator cal){
		System.out.println(cal.divide(9, 3));
	}

	private static void multiply(Calculator cal){
		System.out.println(cal.multiply(2,6));
	}

	private static void subtract(Calculator cal){
		System.out.println(cal.subtract(5,4));
	}

	private static void add(Calculator cal){
		System.out.println(cal.add(3,4));
	}
}

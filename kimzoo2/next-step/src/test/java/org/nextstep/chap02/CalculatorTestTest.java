package org.nextstep.chap02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTestTest {

	private Calculator cal;

	@BeforeEach
	// @Before 애노테이션을 사용해야만 @RunWith, @Rule에서 초기화된 객체에 접근할 수 있다.
	void setUp() {
		cal = new Calculator();
	}

	@Test
	void add() {
		// Calculator cal = new Calculator(); 중복 제거
		// System.out.println(cal.add(6,3)); 실행 결과를 개발자가 직접 눈으로 확인해야 한다는 단점이 있다.
		assertEquals(9, cal.add(6,3));
	}

	@Test
	void subtract() {
		// Calculator cal = new Calculator();
		// System.out.println(cal.subtract(6,3));
		assertEquals(3, cal.subtract(6,3));
	}
}

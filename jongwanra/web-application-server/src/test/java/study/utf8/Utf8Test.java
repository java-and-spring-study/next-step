package study.utf8;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class Utf8Test {

	@Test
	public void test() {
		assertThat((int)'A').isEqualTo(65);
		Double expected = 10 * Math.pow(16, 3) + 12 * Math.pow(16, 2);

		assertThat((int)'가').isEqualTo(expected.intValue())
			.as(" '가'를 16진수로 0xAC00이다. 이걸 10진수로 바꾸면 위와 같은 결과가 나온다.");
	}

}

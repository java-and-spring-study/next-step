package webserver;

import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
public class RequestHandlerTest {

	@Test
	public void 문자열의_getBytes_메서드는_각_문자를_아스키코드로_변환하는_메서드이다() {
		// given
		byte[] bytes = "Hello World".getBytes();

		assertThat('H', is((char) bytes[0]));
		assertThat('e', is((char) bytes[1]));
		assertThat('l', is((char) bytes[2]));
		assertThat('l', is((char) bytes[3]));
		assertThat('o', is((char) bytes[4]));
		assertThat(' ', is((char) bytes[5]));
		assertThat('W', is((char) bytes[6]));
		assertThat('o', is((char) bytes[7]));
		assertThat('r', is((char) bytes[8]));
		assertThat('l', is((char) bytes[9]));
		assertThat('d', is((char) bytes[10]));

	}

}

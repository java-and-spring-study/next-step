package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class HttpRequestTest {
	private String testDirectory = "./src/test/resources/";

	/**
	 * Stream은 용어를 해석하면 '줄기'이다.
	 * Java에서 Stream은 끊김 없는 연속적인 데이터를 의미한다.
	 * Stream은 바이트 단위로 데이터를 가지고 있다.
	 * 이러한 바이트 단위의 데이터들을 char로 변경하기 위해서는 Reader와 Writer를 사용한다.
	 *
	 * @throws Exception
	 */
	@Test
	public void request_GET() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "HTTP_GET.txt"));
		HttpRequest httpRequest = new HttpRequest(in);

		assertThat(httpRequest.getMethod()).isEqualTo("GET");
		assertThat(httpRequest.getPath()).isEqualTo("/user/create");
		assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
		assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
	}

	@Test
	public void request_POST() throws Exception {
		FileInputStream in = new FileInputStream(new File(testDirectory + "HTTP_POST.txt"));
		HttpRequest httpRequest = new HttpRequest(in);

		assertThat(httpRequest.getMethod()).isEqualTo("POST");
		assertThat(httpRequest.getPath()).isEqualTo("/user/create");
		assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
		assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
	}
}

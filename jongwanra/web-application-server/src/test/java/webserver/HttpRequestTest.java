package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.Test;

public class HttpRequestTest {
	private String testDirectory = "./src/test/resources/";

	@Test
	public void request_GET() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "HTTP_GET.txt"));
		HttpRequest httpRequest = new HttpRequest(in);

		assertThat(httpRequest.getMethod()).isEqualTo("GET");
		assertThat(httpRequest.getPath()).isEqualTo("/user/create");
		assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
		assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");


	}
}

package webserver;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.Test;

public class RequestLineTest {

	@Test
	public void create_method() {
		RequestLine requestLine = new RequestLine("GET /index.html HTTP/1.1");
		assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
		assertThat(requestLine.getPath()).isEqualTo("/index.html");

		requestLine = new RequestLine("POST /index.html HTTP/1.1");
		assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
		assertThat(requestLine.getPath()).isEqualTo("/index.html");
	}

	@Test
	public void create_path_and_params() {
		RequestLine requestLine = new RequestLine("GET /user/create?userId=javajigi&password=pass HTTP/1.1");

		assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
		assertThat(requestLine.getPath()).isEqualTo("/user/create");
		
		Map<String, String> params = requestLine.getParams();
		assertThat(params.get("userId")).isEqualTo("javajigi");
		assertThat(params.get("password")).isEqualTo("pass");
		assertThat(params.size()).isEqualTo(2);
	}

}

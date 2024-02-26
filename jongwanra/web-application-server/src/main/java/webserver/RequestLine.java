package webserver;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;

public class RequestLine {
	private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

	private HttpMethod method;
	private String path;
	private Map<String, String> params = new HashMap<>();

	public RequestLine(String requestLine) {
		log.debug("requestLine = {}", requestLine);
		String[] tokens = requestLine.split(" ");

		if (tokens.length != 3) {
			throw new IllegalStateException(requestLine + "이 형식에 맞지 않습니다.");
		}

		method = HttpMethod.findBy(tokens[0]);
		this.path = extractPath(tokens[1]);
		parseQueryStringIfExist(tokens[1]);
	}

	private void parseQueryStringIfExist(String uri) {
		if (method.isPost()) {
			return;
		}
		parseQueryString(extractQueryString(uri));
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getParams() {
		return params;
	}

	private String extractPath(String requestUri) {
		if (requestUri.contains("?")) {
			return requestUri.split("\\?")[0];
		}
		return requestUri;
	}

	private String extractQueryString(String requestUri) {
		if (!requestUri.contains("?")) {
			return null;
		}
		return requestUri.split("\\?")[1];
	}

	private void parseQueryString(String queryString) {
		if (queryString == null || queryString.isEmpty()) {
			return;
		}
		this.params = HttpRequestUtils.parseQueryString(queryString);
	}

}

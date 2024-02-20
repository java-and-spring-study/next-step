package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;
import util.IOUtils;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	private String method;
	private String requestUri;
	private int contentLength;
	private Map<String,String> cookies = new HashMap<>();
	private Map<String, String> queryStrings = new HashMap<>();

	private Map<String, String> bodies = new HashMap<>();

 	private HttpRequest() {}

	public String getMethod() {
		return method;
	}

	public Map<String, String> getBodies() {
		return bodies;
	}

	public void setBodies(Map<String, String> bodies) {
		this.bodies = bodies;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public Map<String, String> getQueryStrings() {
		return queryStrings;
	}

	public boolean isLogin() {
		final String value = cookies.get("logined");
		if(value == null) {
			 return false;		 }
		 return Boolean.parseBoolean(value);
	}

	public int getContentLength() {
		return contentLength;
	}

	public static HttpRequest parse(BufferedReader bufferedReader) throws IOException {
		HttpRequest httpRequest = new HttpRequest();
		String line;
		while(!(line = bufferedReader.readLine()).equals("")) {
			if(line.startsWith("GET") || line.startsWith("POST")) {
				String[] httpSentence = line.split(" ");
				httpRequest.method = httpSentence[0];
				httpRequest.requestUri = httpSentence[1];
				parseQueryString(httpRequest);

			}else if(line.startsWith("Content-Length")) {
				httpRequest.contentLength = Integer.parseInt(line.split(": ")[1]);
			}else if(line.startsWith("Cookie : ")) {
				httpRequest.cookies = HttpRequestUtils.parseCookies(line.substring(8));
			}
		}

		final String body =  IOUtils.readData(bufferedReader, httpRequest.contentLength);
		httpRequest.bodies = HttpRequestUtils.parseQueryString(body);
		return httpRequest;
	}

	private static void parseQueryString(HttpRequest httpRequestHeader) {
		if(httpRequestHeader.requestUri.contains("\\?")) {
			String queryString = httpRequestHeader.requestUri.split("\\?")[1];
			httpRequestHeader.queryStrings = HttpRequestUtils.parseQueryString(queryString);
		}
	}

	@Override
	public String toString() {
		return "HttpRequest{" +
			"method='" + method + '\'' +
			", requestUri='" + requestUri + '\'' +
			", contentLength=" + contentLength +
			", cookies=" + cookies +
			", queryStrings=" + queryStrings +
			'}';
	}

	public boolean isHtmlFile() {
		return requestUri.contains(".html");
	}

	public boolean isCssFile() {
		 return requestUri.endsWith(".css");
 	}

}

package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;
import util.IOUtils;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	private Map<String, String> headerMap = new HashMap<>();
	private Map<String,String> cookieMap = new HashMap<>();
	private Map<String, String> parameterMap = new HashMap<>();

	private Map<String, String> bodyMap = new HashMap<>();

	public String getMethod() {
		return headerMap.get("method");
	}

	public Map<String, String> getBodyMap() {
		return bodyMap;
	}


	public boolean isLogin() {
		final String value = cookieMap.get("logined");
		if(value == null) {
			 return false;
		}
	 	return Boolean.parseBoolean(value);
	}


	public HttpRequest(InputStream in) throws IOException{
		BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in)));

		final String firstLine = bufferedReader.readLine();
		String[] httpSentence = firstLine.split(" ");
		headerMap.put("method", httpSentence[0]);
		headerMap.put("path", extractPath(httpSentence[1]));
		parseQueryString(extractQueryString(httpSentence[1]));

		String line;
		while(!(line = bufferedReader.readLine()).equals("")) {
			String[] pair = line.split(": ");
			headerMap.put(pair[0], pair[1]);
		}

		handleCookie();

		// log.debug("headerMap = {}", headerMap);

		final String body =  IOUtils.readData(bufferedReader, Integer.parseInt(headerMap.getOrDefault("Content-Length", "0")));
		this.bodyMap = HttpRequestUtils.parseQueryString(body);
	}

	private void handleCookie() {
		final String cookieToParse = headerMap.get("Cookie");

		if(cookieToParse == null) {
			return;
		}

		cookieMap = HttpRequestUtils.parseCookies(cookieToParse);
		log.debug("isHtmlFile() = {}", isHtmlFile());
		log.debug("cookieMap = {}", cookieMap);

	}

	private String extractQueryString(String requestUri) {
		if(!requestUri.contains("?")) {
			return null;
		}
		return requestUri.split("\\?")[1];
	}

	private String extractPath(String requestUri) {
		if(requestUri.contains("?")) {
			return requestUri.split("\\?")[0];
		}
		return requestUri;
	}

	private  void parseQueryString(String queryString) {
		if(queryString == null || queryString.isEmpty()) {
			return;
		}
		parameterMap = HttpRequestUtils.parseQueryString(queryString);
	}

	public boolean isHtmlFile() {
		return getPath().contains(".html");
	}

	public boolean isCssFile() {
		 return getPath().endsWith(".css");
 	}

	public String getPath() {
		return headerMap.get("path");
	}

	public String getHeader(String key) {
		return headerMap.get(key);
	}

	public String getParameter(String key) {
		return parameterMap.get(key);
	}
}

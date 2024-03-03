package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import util.HttpRequestUtils;

public class HttpRequest {

	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

	@Getter
	private HttpMethod httpMethod;
	@Getter
	private String path;
	private List<String> headerLines = new ArrayList<>();
	private Map<String, String> parameters;

	public HttpRequest(InputStream in) throws IOException {
		setHttpMethod(in);
	}

	private void setHttpMethod(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String line = br.readLine();
		log.debug("request line : {}", line);

		if(line == null){
			return;
		}
		String[] tokens = line.split(" ");
		int index = tokens[1].indexOf("?");
		setHttpMethod(tokens[0]);
		setPath(tokens[1], index);
		if(index > -1){
			setQueryStringParameters(tokens[1], index);
		}
		setHeaderLines(br, line);
	}

	private void setHttpMethod(String token){
		this.httpMethod = HttpMethod.findByName(token);
	}

	private void setPath(String url, int index){
		this.path = url.substring(0, index);
	}

	private void setHeaderLines(BufferedReader br, String line) throws IOException {
		while(line != null && !line.equals("")){
			log.debug("header : {}", line);
			headerLines.add(line);
			line = br.readLine();
		}
	}
	private void setQueryStringParameters(String url, int index) {
		String queryString = url.substring(index + 1);
		parameters = HttpRequestUtils.parseQueryString(queryString);

	}

	public String getHeader(String key){
		return headerLines.stream()
			.filter(line -> line.contains(key))
			.map(line -> line.split(":")[1].trim())
			.findFirst()
			.orElse(null);
	}

	public String getParameter(String key){
		return parameters.get(key);
	}
}

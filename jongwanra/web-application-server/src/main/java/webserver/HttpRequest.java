package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private Map<String, String> headerMap = new HashMap<>();
    private Map<String, String> cookieMap = new HashMap<>();
    private Map<String, String> parameterMap = new HashMap<>();

    private RequestLine requestLine;

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public Map<String, String> getParameterMap() {
        return this.parameterMap;
    }

    public boolean isLoginByCookie() {
        final String value = cookieMap.get("logined");
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }

    public boolean isLoginBySession() {
        HttpSession session = getSession();
        return session.getAttribute("user") != null;
    }

    public HttpCookie getCookies() {
        return new HttpCookie(getHeader("Cookie"));
    }

    public HttpSession getSession() {
        return HttpSessions.getSession(getCookies().getCookie("JSESSIONID"));
    }

    public HttpRequest(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        final String requestLineOrNull = bufferedReader.readLine();
        requestLine = new RequestLine(requestLineOrNull);
        parameterMap = requestLine.getParams();

        String line;
        while (!(line = bufferedReader.readLine()).equals("")) {
            String[] pair = line.split(": ");
            headerMap.put(pair[0], pair[1]);
        }

        handleCookie();
        handleParameterForBody(bufferedReader);

    }

    private void handleParameterForBody(BufferedReader bufferedReader) throws IOException {
        final int contentLength = Integer.parseInt(headerMap.getOrDefault("Content-Length", "0"));
        if (contentLength <= 0) {
            return;
        }
        final String body = IOUtils.readData(bufferedReader, contentLength);
        this.parameterMap = HttpRequestUtils.parseQueryString(body);
    }

    private void handleCookie() {
        final String cookieToParse = headerMap.get("Cookie");

        if (cookieToParse == null) {
            return;
        }

        cookieMap = HttpRequestUtils.parseCookies(cookieToParse);
        log.debug("isHtmlFile() = {}", isHtmlFile());
        log.debug("cookieMap = {}", cookieMap);

    }

    public boolean isHtmlFile() {
        return getPath().contains(".html");
    }

    public boolean isCssFile() {
        return getPath().endsWith(".css");
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String key) {
        return headerMap.get(key);
    }

    public String getParameter(String key) {
        return parameterMap.get(key);
    }
}

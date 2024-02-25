package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private final DataOutputStream dos;

	public HttpResponse(OutputStream outputStream) {
		this.dos = new DataOutputStream(outputStream);
	}

	private static final String HOST = "http://localhost:8080";
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

	public void addHeader(String key, String value) {
		try {
			dos.writeBytes(key + ": " + value + "\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void forward(String path) {

	}

	public void forwardBody(String path) {

	}

	public void response200Header(int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void responseBody(byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void sendRedirect(String path) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found\r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Location: " + HOST + path + "\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	public void processHeaders() {
		try {
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}
	
	public void responseCssHeader(final int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

}


package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import enums.HttpStatus;
import model.User;

public class HttpResponse {
	private final DataOutputStream dos;

	public HttpResponse(DataOutputStream dos) {
		this.dos = dos;
	}

	private static final String INDEX_PAGE = "http://localhost:8080/index.html";
	private static final String LOGIN_PAGE = "http://localhost:8080/user/login.html";
	private static final String LOGIN_FAILED_PAGE = "http://localhost:8080/user/login_failed.html";
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

	public void handleCssFile(final String path) throws IOException {
		byte[] body = Files.readAllBytes(new File("webapp" + path).toPath());
		responseCssHeader(body.length);
		responseBody(body);
	}

	public void responseLoginHeader(int lengthOfBodyContent, boolean isLoginSuccess) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found\r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("Set-Cookie: logined=" + (isLoginSuccess ? "true" : "false")+";path=/" + "\r\n");

			if(isLoginSuccess) {
				dos.writeBytes("Location: " + INDEX_PAGE + "\r\n") ;
			}else {
				dos.writeBytes("Location: " + LOGIN_FAILED_PAGE + "\r\n");
			}
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void handleHtmlFile(String path, boolean isLogin) throws IOException {
		if(path.startsWith("/user/list") && !isLogin) {
			responseHeader(0, HttpStatus.REDIRECT, LOGIN_PAGE);
			responseBody( new byte[]{});
			return;
		}
		handleStaticFile(path);
	}

	public void handleCreateUser() {
		responseHeader(0, HttpStatus.REDIRECT, INDEX_PAGE);
		responseBody(new byte[]{});
	}

	public void handleLogin(User user) {
		if(user == null) {
			responseLoginHeader(0, false);
			responseBody( new byte[]{});
			return;
		}

		responseLoginHeader( 0, true);
		responseBody( new byte[]{});
	}

	public void handleDefault() {
		byte[] body = "Hello World".getBytes();
		response200Header( body.length);
		responseBody( body);
	}

	private void responseHeader(int lengthOfBodyContent, HttpStatus httpStatus, final String redirectUrl) {
		try {
			dos.writeBytes("HTTP/1.1 "
				+ httpStatus.getCode()
				+ " "
				+ httpStatus.getMessage() + "\r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");

			if(httpStatus.equals(HttpStatus.REDIRECT)) {
				dos.writeBytes("Location: " + redirectUrl);
			}
			dos.writeBytes("\r\n");


		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void responseBody(byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	private void handleStaticFile(final String filePath)  {
		try{
			byte[] body = Files.readAllBytes(new File("webapp" + filePath).toPath());
			responseHeader(body.length, HttpStatus.OK, null);
			responseBody(body);
		}catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	private void response200Header(int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void responseCssHeader(final int lengthOfBodyContent) {
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


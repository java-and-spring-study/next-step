package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import enums.HttpStatus;

public class HttpResponse {
	private static final String INDEX_PAGE = "http://localhost:8080/index.html";
	private static final String LOGIN_PAGE = "http://localhost:8080/user/login.html";
	private static final String LOGIN_FAILED_PAGE = "http://localhost:8080/user/login_failed.html";

	public void handleCssFile(DataOutputStream dos, final  String filePath) throws IOException {
		byte[] body = Files.readAllBytes(new File("webapp" + filePath).toPath());
		responseCssHeader(dos, body.length);
		responseBody(dos, body);
	}

	public void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// log.error(e.getMessage());
		}
	}

	public void responseCssHeader(DataOutputStream dos, final int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// log.error(e.getMessage());
		}
	}

	public void responseLoginHeader(DataOutputStream dos, int lengthOfBodyContent, boolean isLoginSuccess) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found\r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("Set-Cookie: logined=" + (isLoginSuccess ? "true" : "false") + "\r\n");

			if(isLoginSuccess) {
				dos.writeBytes("Location: " + INDEX_PAGE + "\r\n") ;
			}else {
				dos.writeBytes("Location: " + LOGIN_FAILED_PAGE + "\r\n");
			}
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// log.error(e.getMessage());
		}
	}

	public void handleStaticFileV2(DataOutputStream dos, final String filePath, HttpStatus httpStatus) throws IOException {
		byte[] body = Files.readAllBytes(new File("webapp" + filePath).toPath());
		responseHeader(dos, body.length, httpStatus, null);
		responseBody(dos, body);

	}

	public void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			// log.error(e.getMessage());
		}
	}

	public void responseHeader(DataOutputStream dos, int lengthOfBodyContent, HttpStatus httpStatus, final String redirectUrl) {
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
			System.out.println(e.getMessage());
			// log.error(e.getMessage());
		}
	}
}


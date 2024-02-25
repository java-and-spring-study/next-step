package webserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import service.UserService;
import service.input.UserCreateInput;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	private static final String LOGIN_PATH = "/user/login.html";
	private static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
	private static final String INDEX_PATH = "/index.html";

	private Socket connection;
	private final UserService userService;

	public RequestHandler(Socket connectionSocket, UserService userService) {
		this.connection = connectionSocket;
		this.userService = userService;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());
		try (
			InputStream in = connection.getInputStream();
			OutputStream out = connection.getOutputStream();
		) {

			// TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
			HttpRequest httpRequest = new HttpRequest(in);
			HttpResponse httpResponse = new HttpResponse(out);
			final String path = httpRequest.getPath();
			final boolean isLogin = httpRequest.isLogin();

			if (httpRequest.isCssFile()) {
				handleCssFile(httpResponse, path);
				return;
			}

			if (httpRequest.isHtmlFile()) {
				handleHtmlFile(httpResponse, path, isLogin);
				return;
			}

			if (path.startsWith("/user/create")) {
				userService.createUser(UserCreateInput.of(httpRequest.getParameterMap()));
				handleCreateUser(httpResponse);
				return;
			}

			if (path.startsWith("/user/login")) {
				Map<String, String> parsedBody = httpRequest.getParameterMap();
				handleLogin(httpResponse, userService.findOrNullBy(parsedBody.get("userId")));
				return;
			}
			handleDefault(httpResponse);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void handleCssFile(HttpResponse httpResponse, String path) throws IOException {
		byte[] body = Files.readAllBytes(new File("webapp" + path).toPath());
		httpResponse.responseCssHeader(body.length);
		httpResponse.responseBody(body);
	}

	private void handleHtmlFile(HttpResponse httpResponse, String path, boolean isLogin) throws IOException {
		if (path.startsWith("/user/list") && !isLogin) {
			httpResponse.sendRedirect(INDEX_PATH);
			httpResponse.responseBody(new byte[] {});
			return;
		}
		handleStaticFile(httpResponse, path);
	}

	private void handleStaticFile(HttpResponse httpResponse, final String filePath) {
		try {
			byte[] body = Files.readAllBytes(new File("webapp" + filePath).toPath());
			httpResponse.response200Header(body.length);
			httpResponse.responseBody(body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void handleCreateUser(HttpResponse httpResponse) {
		httpResponse.sendRedirect(INDEX_PATH);
		httpResponse.processHeaders();
	}

	private void handleLogin(HttpResponse httpResponse, User user) throws IOException {
		if (user == null) {
			httpResponse.sendRedirect(LOGIN_FAILED_PATH);
			httpResponse.processHeaders();
			httpResponse.responseBody(new byte[] {});
			return;
		}
		httpResponse.sendRedirect(INDEX_PATH);
		httpResponse.addHeader("Set-Cookie", "logined=true;path=/");
		httpResponse.processHeaders();
		httpResponse.responseBody(new byte[] {});
	}

	private void handleDefault(HttpResponse httpResponse) {
		byte[] body = "Hello World".getBytes();
		httpResponse.response200Header(body.length);
		httpResponse.responseBody(body);
	}
}

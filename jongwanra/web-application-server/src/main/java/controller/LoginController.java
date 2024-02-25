package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import model.User;
import service.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class LoginController extends AbstractController {
	private static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
	private static final String INDEX_PATH = "/index.html";
	private final UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@Override
	void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		Map<String, String> parsedBody = httpRequest.getParameterMap();
		User user = userService.findOrNullBy(parsedBody.get("userId"));

		httpResponse.sendRedirect(user == null ? LOGIN_FAILED_PATH : INDEX_PATH);
		if (user != null) {
			httpResponse.addHeader("Set-Cookie", "logined=true;path=/");
		}
		httpResponse.processHeaders();
		httpResponse.responseBody(new byte[] {});
	}

	@Override
	void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		byte[] body = Files.readAllBytes(new File("webapp" + httpRequest.getPath()).toPath());
		httpResponse.response200Header(body.length);
		httpResponse.responseBody(body);
	}
}

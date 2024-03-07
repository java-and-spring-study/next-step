package controller;

import service.UserService;
import service.input.UserCreateInput;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class CreateUserController extends HttpServlet {
	private static final String INDEX_PATH = "/index.html";
	private final UserService userService;

	public CreateUserController(UserService userService) {
		this.userService = userService;
	}

	@Override
	void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		userService.createUser(UserCreateInput.of(httpRequest.getParameterMap()));

		httpResponse.sendRedirect(INDEX_PATH);
		httpResponse.processHeaders();
	}

}

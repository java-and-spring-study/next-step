package webserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import controller.Controller;
import controller.CreateUserController;
import controller.ListUserController;
import controller.LoginController;
import service.UserService;

public class HandlerMapping {
	private Map<String, Controller> uriToControllerMap = new ConcurrentHashMap<>();

	public HandlerMapping(UserService userService) {
		uriToControllerMap.put("/user/create", new CreateUserController(userService));
		uriToControllerMap.put("/user/login", new LoginController(userService));
		uriToControllerMap.put("/user/list", new ListUserController());
	}

	public Controller get(String uri) {
		return uriToControllerMap.get(uri);
	}

}

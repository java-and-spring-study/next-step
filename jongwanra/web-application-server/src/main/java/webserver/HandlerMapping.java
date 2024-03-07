package webserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import controller.CreateUserController;
import controller.ListUserController;
import controller.LoginController;
import controller.Servlet;
import service.UserService;

public class HandlerMapping {
	private Map<String, Servlet> controllerForUriMap = new ConcurrentHashMap<>();

	public HandlerMapping(UserService userService) {
		controllerForUriMap.put("/user/create", new CreateUserController(userService));
		controllerForUriMap.put("/user/login", new LoginController(userService));
		controllerForUriMap.put("/user/list", new ListUserController());
	}

	public Servlet get(String uri) {
		return controllerForUriMap.get(uri);
	}

}

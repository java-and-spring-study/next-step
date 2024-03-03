package webserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import controller.Controller;
import controller.CreateUserController;
import controller.ListUserController;
import controller.LoginController;
import service.UserService;

public class HandlerMapper {
	private Map<String, Controller> controllerForUriMap = new ConcurrentHashMap<>();

	public HandlerMapper(UserService userService) {

		controllerForUriMap.put("/user/create", new CreateUserController(userService));
		controllerForUriMap.put("/user/login", new LoginController(userService));
		controllerForUriMap.put("/user/list", new ListUserController());
	}

	public Controller get(String uri) {
		return controllerForUriMap.get(uri);
	}

}

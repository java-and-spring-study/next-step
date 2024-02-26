package webserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import controller.Controller;
import controller.CreateUserController;
import controller.DefaultController;
import controller.IndexController;
import controller.ListUserController;
import controller.LoginController;
import service.UserService;

public class HandlerMapper {
	private Map<String, Controller> controllerForUriMap = new ConcurrentHashMap<>();

	public HandlerMapper(UserService userService) {
		LoginController loginController = new LoginController(userService);
		CreateUserController createUserController = new CreateUserController(userService);
		DefaultController defaultController = new DefaultController();

		controllerForUriMap.put("/user/create", createUserController);
		controllerForUriMap.put("/user/form.html", createUserController);
		controllerForUriMap.put("/user/login", loginController);
		controllerForUriMap.put("/user/login.html", loginController);
		controllerForUriMap.put("/user/list", new ListUserController());
		controllerForUriMap.put("/index.html", new IndexController());
		controllerForUriMap.put("/user/login_failed.html", loginController);
		controllerForUriMap.put("/", defaultController);
		controllerForUriMap.put("", defaultController);
	}

	public Controller get(String uri) {
		return controllerForUriMap.get(uri);
	}

	public Controller getOrDefault(String path, Controller controller) {
		Controller controllerOrNull = controllerForUriMap.get(path);
		return controllerOrNull == null ? controller : controllerOrNull;
	}
}

package core.servlet;

import java.util.HashMap;
import java.util.Map;

import next.controller.CreateUserController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.UpdateUserController;
import next.controller.UpdateUserFormController;

public class RequestMapping {
	private Map<String, Controller> map = new HashMap<>();

	public RequestMapping() {
		map.put("/", new HomeController());
		map.put("/users", new ListUserController());
		map.put("/users/create", new CreateUserController());
		map.put("/users/login", new LoginController());
		map.put("/users/logout", new LogoutController());
		map.put("/users/profile", new ProfileController());
		map.put("/users/update", new UpdateUserController());
		map.put("/users/updateForm", new UpdateUserFormController());
		map.put("/users/loginForm", new ForwardController("/user/login.jsp"));
		map.put("/users/form", new ForwardController("/user/form.jsp"));
	}

	public Controller getController(String path){
		return map.get(path);
	}
}

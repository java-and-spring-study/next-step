package next;

import java.util.HashMap;
import java.util.Map;

import next.controller.*;

/**
 * path에 맞는 Controller를 가져온다.
 */
public class RequestMapping {
	private Map<String, Controller> mappings = new HashMap<>();

	public RequestMapping() {
		init();
	}
	void init() {
		mappings.put("/", new HomeController());
		mappings.put("/users/form", new ForwardController("/user/form.jsp"));
		mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
		mappings.put("/users", new ListUserController());
		mappings.put("/users/login", new LoginController());
		mappings.put("/users/profile", new ProfileController());
		mappings.put("/users/logout", new LogoutController());
		mappings.put("/users/create", new CreateUserController());
		mappings.put("users/updateForm", new UpdateFormUserController());
		mappings.put("/users/update", new UpdateUserController());

		System.out.println("initialized RequestMapping!");
	}

	public Controller findController(String url) {
		return mappings.get(url);
	}

	void put(String url, Controller controller) {
		mappings.put(url, controller);
	}



	public Controller handle(String path) {
		return mappings.get(path);
	}
}

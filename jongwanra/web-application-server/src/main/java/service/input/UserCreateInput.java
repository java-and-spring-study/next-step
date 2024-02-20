package service.input;

import java.util.Map;

public class UserCreateInput {
	private String userId;
	private String password;
	private String name;
	private String email;



	public UserCreateInput(String userId, String password, String name, String email) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public static UserCreateInput of(Map<String, String> body) {
		return new UserCreateInput(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}

package service;

import db.DataBase;
import model.User;
import service.input.UserCreateInput;

public class UserService {
	public void createUser(UserCreateInput input) {
		User user = new User(input.getUserId(), input.getPassword(), input.getName(), input.getEmail());
		DataBase.addUser(user);
	}

	public User findOrNullBy(String userId) {
		return DataBase.findUserById(userId);
	}
}

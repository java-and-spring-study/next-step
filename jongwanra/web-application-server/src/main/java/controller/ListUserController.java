package controller;

import java.util.Collection;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class ListUserController extends AbstractController {
	private static final String INDEX_PATH = "/index.html";

	@Override
	void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

	}

	@Override
	void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		if (!httpRequest.isLogin()) {
			httpResponse.sendRedirect("/user/login.html");
			return;
		}
		System.out.println("here::::");
		Collection<User> users = DataBase.findAll();
		System.out.println("users = " + users);
		StringBuilder sb = new StringBuilder();

		sb.append("<table border='1'>");
		for (User user : users) {
			sb.append("<tr>");
			sb.append("<td>" + user.getUserId() + "</td>");
			sb.append("<td>" + user.getName() + "</td>");
			sb.append("<td>" + user.getEmail() + "</td>");
			sb.append("</tr>");
		}
		// sb.append("</table>");
		httpResponse.forwardBody(sb.toString());

	}
}

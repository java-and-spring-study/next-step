package webserver.servlet;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class RegisterFormController implements Controller {
	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
		httpResponse.setResponsePath("/user/form.html");
	}
}

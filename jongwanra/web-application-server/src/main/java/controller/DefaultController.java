package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class DefaultController extends AbstractController {
	@Override
	void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

	}

	@Override
	void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		byte[] body = "Hello World".getBytes();
		httpResponse.forward("/index.html");
	}
}

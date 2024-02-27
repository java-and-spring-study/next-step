package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class IndexController extends AbstractController {
	@Override
	void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

	}

	@Override
	void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
		httpResponse.forward("/index.html");
	}
}

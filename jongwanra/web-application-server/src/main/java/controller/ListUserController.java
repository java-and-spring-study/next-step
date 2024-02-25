package controller;

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
			httpResponse.sendRedirect(INDEX_PATH);
			httpResponse.responseBody(new byte[] {});
		}

	}
}

package controller;

import java.io.IOException;

import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public abstract class AbstractController implements Controller {
	@Override
	public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		HttpMethod httpMethod = httpRequest.getMethod();
		if (httpMethod.equals(HttpMethod.POST)) {
			doPost(httpRequest, httpResponse);
			return;
		}
		if (httpMethod.equals(HttpMethod.GET)) {
			doGet(httpRequest, httpResponse);
			return;
		}
		return;
	}

	abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

	abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}

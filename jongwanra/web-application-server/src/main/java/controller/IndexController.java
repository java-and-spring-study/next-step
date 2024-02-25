package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class IndexController extends AbstractController {
	@Override
	void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

	}

	@Override
	void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		byte[] body = Files.readAllBytes(new File("webapp" + httpRequest.getPath()).toPath());
		httpResponse.response200Header(body.length);
		httpResponse.responseBody(body);
	}
}

package webserver.servlet;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface Controller {

	void handle(HttpRequest httpRequest, HttpResponse httpResponse);

}

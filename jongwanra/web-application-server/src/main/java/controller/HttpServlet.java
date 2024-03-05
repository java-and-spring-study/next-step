package controller;

import java.io.IOException;

import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public abstract class HttpServlet implements Servlet {
	@Override
	public void service(HttpRequest httpServletRequest, HttpResponse httpServletResponse) throws
		IOException {
		HttpMethod httpMethod = httpServletRequest.getMethod();
		if (httpMethod.equals(HttpMethod.POST)) {
			doPost(httpServletRequest, httpServletResponse);
			return;
		}
		if (httpMethod.equals(HttpMethod.GET)) {
			doGet(httpServletRequest, httpServletResponse);
			return;
		}
	}

	void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
	}

	void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
	}

}

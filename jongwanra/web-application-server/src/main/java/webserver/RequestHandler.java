package webserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	private static final String INDEX_PATH = "/index.html";

	private Socket connection;
	private final Map<String, Controller> handlerMapper;

	public RequestHandler(Socket connectionSocket, Map<String, Controller> handlerMapper) {
		this.connection = connectionSocket;
		this.handlerMapper = handlerMapper;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());
		try (
			InputStream in = connection.getInputStream();
			OutputStream out = connection.getOutputStream();
		) {

			// TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
			HttpRequest httpRequest = new HttpRequest(in);
			HttpResponse httpResponse = new HttpResponse(out);
			final String path = httpRequest.getPath();

			if (httpRequest.isCssFile()) {
				handleCssFile(httpResponse, path);
				return;
			}
			
			Controller handler = handlerMapper.getOrDefault(path, handlerMapper.get("/"));
			handler.service(httpRequest, httpResponse);

		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void handleCssFile(HttpResponse httpResponse, String path) throws IOException {
		byte[] body = Files.readAllBytes(new File("webapp" + path).toPath());
		httpResponse.responseCssHeader(body.length);
		httpResponse.responseBody(body);
	}
}

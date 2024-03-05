package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.UserService;

public class WebServer {
	private static final Logger log = LoggerFactory.getLogger(WebServer.class);
	private static final int DEFAULT_PORT = 8080;

	public static void main(String args[]) throws Exception {
		UserService userService = new UserService();
		HandlerMapping handlerMapping = new HandlerMapping(userService);

		int port = 0;
		if (args == null || args.length == 0) {
			port = DEFAULT_PORT;
		} else {
			port = Integer.parseInt(args[0]);
		}

		try (ServerSocket listenSocket = new ServerSocket(port)) {
			// 클라이언트가 연결될 때 까지 대기한다.
			Socket connection;
			while ((connection = listenSocket.accept()) != null) {
				// 연결이 되고 나면 해당 소켓을 RequestHandler에 위임한다.
				RequestHandler requestHandler = new RequestHandler(connection, handlerMapping);
				requestHandler.start();
			}
		}
	}
}

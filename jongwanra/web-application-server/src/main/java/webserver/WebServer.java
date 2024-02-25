package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.CreateUserController;
import controller.DefaultController;
import controller.IndexController;
import controller.ListUserController;
import controller.LoginController;
import service.UserService;

public class WebServer {
	private static final Logger log = LoggerFactory.getLogger(WebServer.class);
	private static final int DEFAULT_PORT = 8080;

	public static void main(String args[]) throws Exception {

		UserService userService = new UserService();
		LoginController loginController = new LoginController(userService);
		CreateUserController createUserController = new CreateUserController(userService);
		DefaultController defaultController = new DefaultController();

		Map<String, Controller> handlerMapper = new ConcurrentHashMap<>();

		handlerMapper.put("/user/create", createUserController);
		handlerMapper.put("/user/form.html", createUserController);
		handlerMapper.put("/user/login", loginController);
		handlerMapper.put("/user/login.html", loginController);
		handlerMapper.put("/user/list", new ListUserController());
		handlerMapper.put("/index.html", new IndexController());
		handlerMapper.put("/user/login_failed.html", loginController);
		handlerMapper.put("/", defaultController);
		handlerMapper.put("", defaultController);

		int port = 0;
		if (args == null || args.length == 0) {
			port = DEFAULT_PORT;
		} else {
			port = Integer.parseInt(args[0]);
		}

		// 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.

		try (ServerSocket listenSocket = new ServerSocket(port)) {
			// 클라이언트가 연결될때까지 대기한다.
			Socket connection;
			while ((connection = listenSocket.accept()) != null) {
				RequestHandler requestHandler = new RequestHandler(connection, handlerMapper);
				requestHandler.start();
			}
		}
	}
}

package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.UserService;
import service.input.UserCreateInput;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final UserService userService;

    public RequestHandler(Socket connectionSocket, UserService userService) {
        this.connection = connectionSocket;
        this.userService = userService;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in)));
            ) {

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));
            final String requestUri = httpRequest.getRequestUri();
            final boolean isLogin = httpRequest.isLogin();

            if(httpRequest.isCssFile()) {
                httpResponse.handleCssFile(requestUri);
                return;
            }

            if(httpRequest.isHtmlFile()) {
                httpResponse.handleHtmlFile(requestUri, isLogin);
                return;
            }

            if(requestUri.startsWith("/user/create")) {
                userService.createUser(UserCreateInput.of(httpRequest.getBodies()));
                httpResponse.handleCreateUser();
                return;
            }

            if(requestUri.startsWith("/user/login")) {
                Map<String, String> parsedBody = httpRequest.getBodies();
                httpResponse.handleLogin(userService.findOrNullBy(parsedBody.get("userId")));
                return;
            }

            httpResponse.handleDefault();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }



















}

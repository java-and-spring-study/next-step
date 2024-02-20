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

import db.DataBase;
import enums.HttpStatus;
import model.User;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String INDEX_PAGE = "http://localhost:8080/index.html";
    private static final String LOGIN_PAGE = "http://localhost:8080/user/login.html";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in)));
            ) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequest.parse(bufferedReader);
            HttpResponse httpResponse = new HttpResponse();
            final String requestUri = httpRequest.getRequestUri();
            final boolean isLogin = httpRequest.isLogin();

            if(httpRequest.isCssFile()) {
                httpResponse.handleCssFile(dos, requestUri);
                return;
            }

            if(httpRequest.isHtmlFile()) {
                if(requestUri.startsWith("/user/list") && !isLogin) {
                    httpResponse.responseHeader(dos, 0, HttpStatus.REDIRECT, LOGIN_PAGE);
                    httpResponse.responseBody(dos, new byte[]{});
                    return;
                }

                httpResponse.handleStaticFileV2(dos, requestUri, HttpStatus.OK);
                return;
            }

            if(requestUri.startsWith("/user/create")) {
                Map<String, String> parsedBody = httpRequest.getBodies();
                User user = new User(parsedBody.get("userId"), parsedBody.get("password"), parsedBody.get("name"), parsedBody.get("email"));
                DataBase.addUser(user);
                httpResponse.responseHeader(dos, 0, HttpStatus.REDIRECT, INDEX_PAGE);
                httpResponse.responseBody(dos, new byte[]{});
                return;
            }

            if(requestUri.startsWith("/user/login")) {
                Map<String, String> parsedBody = httpRequest.getBodies();
                final String userId = parsedBody.get("userId");

                User foundUser = DataBase.findUserById(userId);
                log.debug("foundUser = {}", foundUser);
                if(foundUser == null) {
                    httpResponse.responseLoginHeader(dos, 0, false);
                    httpResponse.responseBody(dos, new byte[]{});
                    return;
                }

                httpResponse.responseLoginHeader(dos, 0, true);
                httpResponse.responseBody(dos, new byte[]{});
                return;
            }


            byte[] body = "Hello World".getBytes();
            httpResponse.response200Header(dos, body.length);
            httpResponse.responseBody(dos, body);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            // log.error(e.getMessage());
        }
    }



















}

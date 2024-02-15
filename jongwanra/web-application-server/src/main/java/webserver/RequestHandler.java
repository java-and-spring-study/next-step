package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import enums.HttpStatus;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String LOGIN_FAILED_PAGE = "http://localhost:8080/user/login_failed.html";
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
            final String requestUri = httpRequest.getRequestUri();
            final boolean isLogin = httpRequest.isLogin();

            if(isCssFile(requestUri)) {
                handleCssFile(dos, requestUri);
                return;
            }

            if(isStaticFile(requestUri)) {
                if(requestUri.startsWith("/user/list") && !isLogin) {
                    responseHeader(dos, 0, HttpStatus.REDIRECT, LOGIN_PAGE);
                    responseBody(dos, new byte[]{});
                    return;
                }

                handleStaticFileV2(dos, requestUri, HttpStatus.OK);
                return;
            }

            if(requestUri.startsWith("/user/create")) {
                Map<String, String> parsedBody = httpRequest.getBodies();
                User user = new User(parsedBody.get("userId"), parsedBody.get("password"), parsedBody.get("name"), parsedBody.get("email"));
                DataBase.addUser(user);
                responseHeader(dos, 0, HttpStatus.REDIRECT, INDEX_PAGE);
                responseBody(dos, new byte[]{});
                return;
            }

            if(requestUri.startsWith("/user/login")) {
                Map<String, String> parsedBody = httpRequest.getBodies();
                final String userId = parsedBody.get("userId");

                User foundUser = DataBase.findUserById(userId);
                log.debug("foundUser = {}", foundUser);
                if(foundUser == null) {
                    responseLoginHeader(dos, 0, false);
                    responseBody(dos, new byte[]{});
                    return;
                }

                responseLoginHeader(dos, 0, true);
                responseBody(dos, new byte[]{});
                return;
            }


            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);



        } catch (IOException e) {
            System.out.println(e.getMessage());
            // log.error(e.getMessage());
        }
    }

    private void responseCssHeader(DataOutputStream dos, final int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // log.error(e.getMessage());
        }
    }

    private boolean isCssFile(String requestUri) {
        return requestUri.endsWith(".css");
    }

    private boolean isStaticFile(final String value) {
        return value.contains(".html");
    }

    private void handleStaticFileV2(DataOutputStream dos, final String filePath, HttpStatus httpStatus) throws IOException {
        byte[] body = Files.readAllBytes(new File("webapp" + filePath).toPath());
        responseHeader(dos, body.length, httpStatus, null);
        responseBody(dos, body);

    }

    private void handleCssFile(DataOutputStream dos, final  String filePath) throws IOException {
        byte[] body = Files.readAllBytes(new File("webapp" + filePath).toPath());
        responseCssHeader(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // log.error(e.getMessage());
        }
    }


    private void responseLoginHeader(DataOutputStream dos, int lengthOfBodyContent, boolean isLoginSuccess) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Set-Cookie: logined=" + (isLoginSuccess ? "true" : "false") + "\r\n");

            if(isLoginSuccess) {
                dos.writeBytes("Location: " + INDEX_PAGE + "\r\n") ;
            }else {
                dos.writeBytes("Location: " + LOGIN_FAILED_PAGE + "\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // log.error(e.getMessage());
        }
    }




    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, HttpStatus httpStatus, final String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 "
                + httpStatus.getCode()
                + " "
                + httpStatus.getMessage() + "\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");

            if(httpStatus.equals(HttpStatus.REDIRECT)) {
                dos.writeBytes("Location: " + redirectUrl);
            }
            dos.writeBytes("\r\n");


        } catch (IOException e) {
            System.out.println(e.getMessage());
            // log.error(e.getMessage());
        }
    }


    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // log.error(e.getMessage());
        }
    }
}

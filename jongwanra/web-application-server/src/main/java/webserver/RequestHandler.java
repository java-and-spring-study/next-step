package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import db.DataBase;
import enums.HttpStatus;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    // private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String LOGIN_FAILED_PAGE = "http://localhost:8080/user/login_failed.html";
    private static final String INDEX_PAGE = "http://localhost:8080/index.html";
    private static final String USER_LIST_PAGE = "http://localhost:8080/user/list.html";
    private static final String LOGIN_PAGE = "http://localhost:8080/user/login.html";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        System.out.println("New Client Connect! Connected IP : " + connection.getInetAddress() + ", Port : "
                + connection.getPort());
        // log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
        //         connection.getPort());

        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in)));
            ) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);

            Map<String, String> parsedHttpRequestMap = IOUtils.parseHttpRequest(bufferedReader);
            final String requestUri = parsedHttpRequestMap.get("requestUri");
            final String method = parsedHttpRequestMap.get("method");
            final int contentLength = Integer.parseInt(parsedHttpRequestMap.get("contentLength"));
            final boolean isLogin = Boolean.parseBoolean(parsedHttpRequestMap.get("logined") == null ? "false" : parsedHttpRequestMap.get("logined"));

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
                if(method.equals("GET")) {
                    final String queryString = requestUri.split("\\?")[1];
                    Map<String, String> parsedQueryString = HttpRequestUtils.parseQueryString(queryString);
                    User user = new User(parsedQueryString.get("userId"), parsedQueryString.get("password"), parsedQueryString.get("name"), parsedQueryString.get("email"));
                    DataBase.addUser(user);
                }else {
                    String body = IOUtils.readData(bufferedReader, contentLength);
                    Map<String, String> parsedBody = HttpRequestUtils.parseQueryString(body);
                    User user = new User(parsedBody.get("userId"), parsedBody.get("password"), parsedBody.get("name"), parsedBody.get("email"));
                    DataBase.addUser(user);
                }
                responseHeader(dos, 0, HttpStatus.REDIRECT, INDEX_PAGE);
                responseBody(dos, new byte[]{});
                return;

            }

            if(requestUri.startsWith("/user/login")) {
                String body = IOUtils.readData(bufferedReader, contentLength);
                Map<String, String> parsedBody = HttpRequestUtils.parseQueryString(body);
                final String userId = parsedBody.get("userId");
                final String password = parsedBody.get("password");

                User foundUser = DataBase.findUserById(userId);
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

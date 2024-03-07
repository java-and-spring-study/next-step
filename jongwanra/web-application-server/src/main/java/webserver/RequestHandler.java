package webserver;

import controller.Servlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private final HandlerMapping handlerMapping;

    public RequestHandler(Socket connectionSocket, HandlerMapping handlerMapping) {
        this.connection = connectionSocket;
        this.handlerMapping = handlerMapping;
    }

    public void run() {
        try (
                InputStream in = connection.getInputStream();
                OutputStream out = connection.getOutputStream();
        ) {

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);

            if (getSessionId(httpRequest) == null) {
                httpResponse.addHeader("Set-Cookie", "JSESSIONID=" + UUID.randomUUID());
            }
            final String path = httpRequest.getPath();

            Servlet servlet = handlerMapping.get(path);


            if (servlet == null) {
                httpResponse.forward(path);
                return;
            }

            servlet.service(httpRequest, httpResponse);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getSessionId(HttpRequest httpRequest) {
        return httpRequest.getCookies().getCookie("JSESSIONID");
    }

}

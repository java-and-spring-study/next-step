package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    // private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

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

            final String methodAndHost = bufferedReader.readLine();

            if(methodAndHost.contains("index.html")) {
                BufferedReader fileBufferedReader = new BufferedReader(new FileReader("webapp/index.html"));

                StringBuilder sb = new StringBuilder();
                String byteBody;
                while((byteBody = fileBufferedReader.readLine()) != null) {
                    System.out.println("byteBody = " + byteBody);
                    sb.append(byteBody);
                }
                final byte[] body = sb.toString().getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);

                fileBufferedReader.close();
            }else {
                byte[] body = "Hello Worldasasas".getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
            // log.error(e.getMessage());
        }
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

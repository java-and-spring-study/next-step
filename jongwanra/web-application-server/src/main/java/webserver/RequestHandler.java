package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

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

            if(isStaticFile(methodAndHost)) {
                handleStaticFile(dos, methodAndHost.split(" ")[1]);
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

    private boolean isStaticFile(final String value) {
        return value.contains(".html");
    }

    private void handleStaticFile(DataOutputStream dos, final String filePath) throws IOException {
        BufferedReader fileBufferedReader = new BufferedReader(new FileReader("webapp" + filePath));
        StringBuilder stringBuilder = new StringBuilder();

        String byteBody;
        while((byteBody = fileBufferedReader.readLine()) != null) {
            stringBuilder.append(byteBody);
        }
        fileBufferedReader.close();

        final byte[] body = stringBuilder.toString().getBytes();
        response200Header(dos, body.length);
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

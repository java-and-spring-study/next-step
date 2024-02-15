package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IOUtils {
    /**
     * @param
     * BufferedReader는 Request Body를 시작하는 시점이어야
     * @param
     * contentLength는 Request Header의 Content-Length 값이다.z
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }


    // public static Map<String, String> parseHttpRequest(BufferedReader br) throws IOException {
    //     Map<String, String> map = generateDefaultHttpRequest();
    //
    //     String target;
    //     while(!(target = br.readLine()).equals("")){
    //         if(target.startsWith("GET") || target.startsWith("POST")){
    //             String[] httpFirstSentence = target.split(" ");
    //             map.put("method", httpFirstSentence[0]);
    //             map.put("requestUri", httpFirstSentence[1]);
    //         } else if(target.startsWith("Content-Length")) {
    //             map.put("contentLength", target.split(": ")[1]);
    //         } else if(target.startsWith("Cookie: ")) {
    //             Map<String, String> parsedCookieMap = HttpRequestUtils.parseCookies(target.substring(8));
    //             for (String key : parsedCookieMap.keySet()) {
    //                 map.put(key, parsedCookieMap.get(key));
    //             }
    //         }
    //     }
    //     System.out.println("map = " + map);
    //     return map;
    // }

    // private static Map<String, String> generateDefaultHttpRequest() {
    //     Map<String, String> map = new HashMap<>();
    //     map.put("requestUri", null);
    //     map.put("method", null);
    //     map.put("contentLength", "0");
    //
    //     return map;
    // }
}

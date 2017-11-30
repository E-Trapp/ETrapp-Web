package cat.udl.etrapp.server.utils;

import javax.ws.rs.core.HttpHeaders;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Utils {

    public static Map<String, String> generateSessionToken() {
        Map<String, String> tokenData = new HashMap<>();

        final String token = UUID.randomUUID().toString();
        tokenData.put("token", token);
        tokenData.put("hashed", getHashedString(token.getBytes()));
        return tokenData;
    }

    public static String getHashedString(String input) {
        return getHashedString(input.getBytes());
    }

    public static String getHashedString(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return byteArrayToHexString(md.digest(input));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder result = new StringBuilder();
        for (byte aB : b) {
            result.append(Integer.toString((aB & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    public static String getAuthToken(HttpHeaders headers) {
        return headers.getHeaderString(HttpHeaders.AUTHORIZATION).substring("Bearer".length()).trim();
    }

    public static void main(String args[]) {
        System.out.println("Custom token for development: qweqweqweqwe -> " + getHashedString("qweqweqweqwe"));
    }

}

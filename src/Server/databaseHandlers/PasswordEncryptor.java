package Server.databaseHandlers;

import Server.ServerConfig;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

    public String encrypt(String s) {
        final int maxLen = 32;
        final int radix = 16;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            ServerConfig.logger.info("Cant encrypt password");
        }
        byte[] messageDigest = md.digest(s.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        StringBuilder hashtext = new StringBuilder(no.toString(radix));
        while (hashtext.length() < maxLen) {
            hashtext.insert(0, "0");
        }
        return hashtext.toString();
    }

}

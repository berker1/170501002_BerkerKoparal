package inf202;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodingPassword {

    public String encodeMD5(String password){
        String digestText = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i<bytes.length;i++ ){
                sb.append(Integer.toString( (bytes[i] & 0xff) + 0xff , 16).substring(1) );
                digestText = sb.toString();
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return digestText;
    }
}

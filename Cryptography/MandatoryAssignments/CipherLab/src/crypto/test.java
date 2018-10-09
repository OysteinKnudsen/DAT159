package crypto;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class test {
public static void main (String [] args ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey();
    DES des = new DES(secretKey);

    String originalMessage = "Original";
    String encryptedMessage = des.encrypt(originalMessage);
    String decryptedMessage = des.decrypt(encryptedMessage) ;


    System.out.println(encryptedMessage);
    System.out.println(decryptedMessage);

}

}

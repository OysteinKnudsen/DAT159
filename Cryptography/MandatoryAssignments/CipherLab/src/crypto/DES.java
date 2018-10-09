package crypto;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


/**
 *      DES
 *
 *      Key: 64 bit -> 8 byte. For each byte there is 1 parity bit.
 *      Meaning that the actual value of the key is parity + 56-bit key
 *
 *      Output cypher text: 64bit block.
 *
 *      DES top view
 *      -------------
 *      - 16 rounds of operations.
 *      - 16 subkeys are generated, one for each round.
 *
 *      Each round of the encryption
 *      ----------------------------
 *      - The input of each round is the output of the previous round.
 *      - The left half of the output = the right hand of the input.
 *      - The right half of the output = the left hand of input and the result of Mangler Function.
 *
 */

public class DES {

    Cipher encryptionCipher;
    Cipher decryptionCipher;

    DES(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        encryptionCipher = Cipher.getInstance("DES");
        decryptionCipher = Cipher.getInstance("DES");

        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key);
    }

    public String encrypt(String message) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        //Message as bytes
        byte [] msg = message.getBytes("UTF8");

        //encrypt message
        byte [] encryptedMessage = encryptionCipher.doFinal(msg);

        //Convert to standard UTF string.
        return new String (Base64.getEncoder().encode(encryptedMessage));
    }

    public String decrypt (String encryptedMessage) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        byte [] msg = Base64.getDecoder().decode(encryptedMessage);

        byte [] decryptedMessage = decryptionCipher.doFinal(msg);

        return new String (decryptedMessage, "UTF8");
    }
}

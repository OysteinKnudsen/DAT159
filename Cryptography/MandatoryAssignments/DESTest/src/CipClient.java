import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CipClient
{

    public static void main(String[] args) throws Exception
    {
        final String ORIGINAL_MESSAGE = "This is super secret";
        final String HOST= "localhost";
        final int PORT = 7999;

        Key key = generateKey();

        storeToFile(key);

        Socket socket = new Socket(HOST, PORT);

        byte [] encryptedMessage = encryptMessage(ORIGINAL_MESSAGE.getBytes(), key);

        sendMessage(socket, encryptedMessage);
    }

    private static void storeToFile(Key key) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("KeyFile"));
        out.writeObject(key);
        out.close();
    }

    private static Key generateKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("DES");
        generator.init(new SecureRandom());
        return generator.generateKey();
    }

    private static byte [] encryptMessage (byte [] plaintext, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher encryptionCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptionCipher.doFinal(plaintext);
    }
    private static void sendMessage (Socket socket, byte [] message) throws IOException {
        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
        dataOut.writeInt(message.length);
        dataOut.write(message);
    }
}
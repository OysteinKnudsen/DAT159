/**
 * 
 */
package crypto;

import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Client {

	public static void main(String[] args) {
		Client client = new Client();
		client.sendAndRecieve();
	}

	public void sendAndRecieve() {
		final String ORIGINAL_MESSAGE = "The government is listening!";
		final String HOST = "localhost";
		final int PORT = 9090;
		Socket socket;
		DataOutputStream dataOut;
		DataInputStream dataIn;


		try {

			//Generate and store key to file.
			Key key = generateKey();
			storeToFile(key);

			socket = new Socket(HOST, PORT);

			System.out.println("Connected to Server on " + socket.getInetAddress());

			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());

			byte[] encryptedMessage = encryptMessage(ORIGINAL_MESSAGE.getBytes(), key);

			sendMessage(encryptedMessage, socket);

			byte [] responseFromServer = readMessage(socket);

			System.out.println("Response form server encrypted: " + new String (responseFromServer));

			String decryptedMessage = new String (decryptMessage(responseFromServer, key));
			System.out.println("Response from server decrypted: " + decryptedMessage);


		} catch (
				NoSuchAlgorithmException
				| IOException
				| NoSuchPaddingException
				| InvalidKeyException
				| BadPaddingException
				| IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		}
	}

		private static void sendMessage ( byte[] message, Socket socket) throws IOException {
			DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
			dataOut.writeInt(message.length);
			dataOut.write(message);
		}

		private static byte[] encryptMessage ( byte[] plaintext, Key key) throws
		NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
		{
			Cipher encryptionCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
			return encryptionCipher.doFinal(plaintext);
		}

		private static byte[] decryptMessage ( byte[] message, Key key) throws
		NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
		{
			Cipher decryptionCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			decryptionCipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptedMessage = decryptionCipher.doFinal(message);
			return decryptedMessage;
		}

		private static byte[] readMessage (Socket socket) throws IOException {
			DataInputStream dataIn = new DataInputStream(socket.getInputStream());
			int lengthOfMessage = dataIn.readInt();
			byte[] message = null;
			if (lengthOfMessage > 0) {
				message = new byte[lengthOfMessage];
				dataIn.readFully(message, 0, lengthOfMessage);
			}
			return message;
		}

		private static Key generateKey () throws NoSuchAlgorithmException {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom());
			return generator.generateKey();
		}

		private static void storeToFile (Key key) throws IOException {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("KeyFile"));
			out.writeObject(key);
			out.close();
		}

	}

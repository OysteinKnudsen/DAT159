/**
 * 
 */
package crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Server {

	  public static void main(String args[])
	  {
		  Server server = new Server();

		  while (true) {
			  server.receiveAndSend();
		  }
	  }
	  

	
	public void receiveAndSend() {
		ServerSocket serverSocket;
	    Socket clientSocket;
		DataInputStream dataIn;
		DataOutputStream dataOut;
	    final int PORT = 9090;
	    Key key;
	    
	    try{

	    	//Setup connections
	    	serverSocket = new ServerSocket(PORT);
	    	System.out.println("Waiting for requests from client...");
	    	clientSocket = serverSocket.accept();
	    	System.out.println("Connected to client at the address: "+clientSocket.getInetAddress());

			dataIn = new DataInputStream(clientSocket.getInputStream());
			dataOut = new DataOutputStream(clientSocket.getOutputStream());

			//Read key from file
			key = readKey();

			//recieve and decrypt message from client
	        byte [] messageFromClient = readMessage(clientSocket);

	        System.out.println("Message before decryption: " + new String (messageFromClient));

			byte [] decryptedMessage = decryptMessage(messageFromClient, key);
	        System.out.println("Message after decryption: " + new String (decryptedMessage));


	        
	        // Create a response to client if message received
	        String response = "No message received";
	        
	        if(dataIn != null){
	        	response = "Message received, keept it on the down low";
	        	
	        	// Send the plaintext response message to the client
				byte [] encryptedResponse = encryptMessage(response.getBytes(), key);
				sendMessage(clientSocket, encryptedResponse);

			}
	        
	        // Close Client and Server sockets
	        clientSocket.close();
	        serverSocket.close();
	        dataOut.close();
	        dataIn.close();
	        
	    }catch(IOException | ClassNotFoundException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e){
	    	e.printStackTrace();
	    }
		
	}
	private static void sendMessage (Socket socket, byte [] message) throws IOException {
		DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
		dataOut.writeInt(message.length);
		dataOut.write(message);
	}

	private static byte [] encryptMessage (byte [] plaintext, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher encryptionCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
		return encryptionCipher.doFinal(plaintext);
	}

	private static byte [] decryptMessage (byte [] message, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher decryptionCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		decryptionCipher.init(Cipher.DECRYPT_MODE, key);
		byte [] decryptedMessage = decryptionCipher.doFinal(message);
		return decryptedMessage;
	}

	private static byte [] readMessage (Socket socket) throws IOException {
		DataInputStream dataIn = new DataInputStream(socket.getInputStream());
		int lengthOfMessage = dataIn.readInt();
		byte [] message = null;
		if (lengthOfMessage > 0){
			message = new byte[lengthOfMessage];
			dataIn.readFully(message, 0, lengthOfMessage);
		}
		return message;
	}

	private static Key readKey() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("KeyFile"));
		Key key = (Key)in.readObject();
		in.close();
		System.out.println("Read key from file");
		return key;
	}
}

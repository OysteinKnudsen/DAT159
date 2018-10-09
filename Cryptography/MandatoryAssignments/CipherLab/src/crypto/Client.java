/**
 * 
 */
package crypto;

import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author tosindo
 *
 */
public class Client implements IParent {
	SecretKey secretKey;
	DES des;

	public Client() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
		secretKey = KeyGenerator.getInstance("DES").generateKey();
		des = new DES(secretKey);
	}


	public static void main(String[] args) throws InterruptedException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
		Client client = new Client();


		while (true) {
			client.sendAndReceice();
			Thread.sleep(1000);
			}
		}

	
	public void sendAndReceice() {
		Socket client;
	    ObjectOutputStream oos;
	    ObjectInputStream ois;
	    
	    try {
	    	client = new Socket("localhost",PORT);
			
	    	System.out.println("Connected to Server on "+ client.getInetAddress());

	    	oos = new ObjectOutputStream(client.getOutputStream());
	    	ois = new ObjectInputStream(client.getInputStream());
	    	
	    	// send a plaintext message to server
	    	String plaintxt = "Hello from client";
	    	String encryptedText = des.encrypt(plaintxt);
	    	// send message to server
	    	oos.writeObject(encryptedText.getBytes());
	    	oos.flush();
	    	
	    	// receive response from server
	    	byte[] response = (byte[]) ois.readObject();
	    	
	    	System.out.println("Response from server: "+ new String(response, "ASCII"));
	    	
	    	// close cliet socket
	    	client.close();
	    	ois.close();
	    	oos.close();
	    	
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}

	}

	@Override
	public byte[] encryptMessage(byte[] plaintext) {
		
		return null;
	}

	@Override
	public byte[] decryptMessage(byte[] ciphertext) {
		
		return null;
	}

}

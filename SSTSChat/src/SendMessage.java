import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;










import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SendMessage {
	//Added a possible key and ciphertext value for encryption
	private CloseableHttpClient httpclient;
	private String receiver;
	private String message;
	private String token;
	private Key EncKey = null;
	private byte[] ciphertext = null;

	public SendMessage() {
		httpclient = HttpClients.createDefault();
	}

	//if a key is not passed (which it always will be now)
	//we do plaintext send + execute
	public SendMessage(String rec, String mes, String tok) {
		httpclient = HttpClients.createDefault();
		receiver = rec;
		message = mes;
		token = tok;

		Execute();
	}

	//If a key IS passed, we encrypt the message
	//and then send THAT
	public SendMessage(String rec, String mes, String tok, Key AESKey) {
		httpclient = HttpClients.createDefault();
		receiver = rec;
		message = mes;
		token = tok;
		EncKey = AESKey;

		if(EncKey != null) {
			try {
				EncryptMessage();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Encryption Broke");
				e.printStackTrace();
			}
		}
	}

	//plaintext
	public void Execute() {
		try {
			System.out.println("POST ---");
			HttpPost httpPost = new HttpPost("https://sstssecurity.com/SendMessage.php");
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("receiver", receiver));
			nvps.add(new BasicNameValuePair("message", message));

			httpPost.addHeader("token", token);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);

			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			//EntityUtils.consume(entity2);
			System.out.println(EntityUtils.toString(entity2));
			response2.close();
		} catch(IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
	}

	//encrypts the message
	private void EncryptMessage() throws Exception {
		System.out.println("UNENCRYPTED : " + message);
		//iv is 16 bytes, cuz 128-bit AES key
		byte[] iv = new byte[16];
		SecureRandom random = new SecureRandom();
		//random iv even though it should be a counter
		//CBC was too hard, so I went with no padding instead
		random.nextBytes(iv);
		IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
		System.out.println("iv[] : " + new String(iv, "UTF-8"));
		//AES encryption, Counter mode, no padding
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");
		
		//encrypts, then stores as byte[]
		cipher.init(Cipher.ENCRYPT_MODE, EncKey, ivParamSpec);
		byte[] cipherText = cipher.doFinal(message.getBytes());
		System.out.println("encrypted : " + new String(cipherText, "UTF-8"));

		//now we append the AES key, AND the iv to the message
		//Still need to encrypt the key, but we have no RSA keys as of yet
		String messageKey = new String(cipherText, "UTF-8") + new String(EncKey.getEncoded(), "UTF-8") + new String(ivParamSpec.getIV(), "UTF-8");
		System.out.println("Message + key: " + messageKey);
		SendEncryption(messageKey);
		
		//calls method to send encrypted byte[]
		//SendEncryption(cipherText);
		
		//ONLY TEST DECRYPTION AFTERWARDS
	    cipher.init(Cipher.DECRYPT_MODE, EncKey, ivParamSpec);
	    byte[] plainText = cipher.doFinal(cipherText);
	    System.out.println("plain : " + new String(plainText, "UTF-8"));
	}

	//similar to regular post, but change byte[] into string
	//public void SendEncryption(byte[] cipherText) {
	public void SendEncryption(String messageKey) {
		try {
			//convert here, commented out becausse now passing a string of m+k
//			String encryptedMessage = new String(cipherText, "UTF-8");
			System.out.println("POST ---");
			HttpPost httpPost = new HttpPost("https://sstssecurity.com/SendMessage.php");
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("receiver", receiver));
			//send here
//			nvps.add(new BasicNameValuePair("message", encryptedMessage));
			nvps.add(new BasicNameValuePair("message", messageKey));

			httpPost.addHeader("token", token);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);

			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			//EntityUtils.consume(entity2);
			System.out.println(EntityUtils.toString(entity2));
			response2.close();
		} catch(IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
	}
}

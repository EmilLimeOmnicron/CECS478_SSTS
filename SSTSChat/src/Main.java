import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.crypto.KeyGenerator;

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
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args) throws Exception {
		int option = 0;
		String token;
		final int jwtStart = 8;
		
		//This just initializes a lot of the security stuff we will need to get the key4
		//right away we call generatAESKey to get a key
	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SecureRandom random = new SecureRandom();
		Key AESKey = GenerateAESKey(random);
		

		while(option != 5) {
			System.out.println("Options");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("5. close");
			Scanner in = new Scanner(System.in);
			option = in.nextInt();
			in.nextLine();
			switch(option) {
			//LOGIN THEN SEND/GET/CLOSE
			case 1: 
				System.out.println("Login: Input username");
				String username = in.nextLine();

				System.out.println("Login: Enter password");
				String password = in.nextLine();
				Login log = new Login(username, password);

				token = log.Execute();
				System.out.println(token);
				if(token.startsWith("{'status' : 'error'")) {
					break;
				}
				String modifiedToken = token.substring(jwtStart, token.length()-2);

				while(option != 5) {
					System.out.println("Options");
					System.out.println("3. Send message");
					System.out.println("4. Get Message");
					System.out.println("5. close");
					option = in.nextInt();
					in.nextLine();
					if(option == 3) {
						System.out.println("SendMessage: Input message receiver.");
						String toPerson = in.nextLine();

						System.out.println("SendMessage: Input message.");
						String message = in.nextLine();
						
						System.out.println("KEY: " + new String(AESKey.getEncoded(), "UTF-8"));
						SendMessage sm = new SendMessage(toPerson, message, modifiedToken, AESKey);
						//sm.Execute();
					}

					else if(option == 4) {

						GetMessage gm = new GetMessage(modifiedToken);
						gm.Execute();

					}

					else if (option == 5) {
						System.out.println("Terminating client");
						System.exit(0);
					}

				}

			case 2: System.out.println("Register: Input username.");
			String registerUsername = in.nextLine();

			System.out.println("Register: Input email.");
			String registerEmail = in.nextLine();

			System.out.println("Register: Input password.");
			String registerPass = in.nextLine();


			Register reg = new Register(registerUsername, registerEmail, registerPass);
			reg.Execute();

			break;
			case 5:
				System.out.println("Terminating client");
				System.exit(0);
				break;

			default: System.out.println("Invalid Option, reinput choice");
			break;
			}
		}
	}

	//Generator makes the key, at this point I only use 128 cuz 256 crashes my code
	private static Key GenerateAESKey(SecureRandom random) throws Exception {
	    KeyGenerator generator;
		generator = KeyGenerator.getInstance("AES", "BC");
		//idk why random, might work with just 128
	    generator.init(128, random);
		Key AESKey = generator.generateKey();
	    try {
	    	System.out.println("KEY: " + new String(AESKey.getEncoded(), "UTF-8"));
	    } catch (UnsupportedEncodingException e) {
	    	System.out.println("Key Broke in generator");
	    }

	    return AESKey;
	}

}
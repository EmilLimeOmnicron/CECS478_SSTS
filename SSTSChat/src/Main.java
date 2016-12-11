
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Scanner;

 
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import java.util.*;
 
import java.io.*;


public class Main {
 
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException {
       int option = 0;
       String token;
       final int jwtStart = 8;
      
       
       
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
              
              SendMessage sm = new SendMessage(toPerson, message, modifiedToken);
              sm.Execute();
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
    	  
      default: System.out.println("Invalid Option, reinput choice");
    	  break;
      }
    }
    }
 
}
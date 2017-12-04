import java.io.*;
import java.net.*;
import java.security.*;
import javax.swing.*;
import javax.crypto.*;
import java.util.*;

/**
*Douglas Kaelin
*HW 9 Research

@author dek4204
*/

public class tester{
   public static void main(String[] args)throws Exception{
      new tester();
   }
   
   public tester()throws Exception{
      GenerateKeys gk=null;
		try {
			gk = new GenerateKeys(1024);
			gk.createKeys();
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
		}
      
      AsymmetricCryptography ac = new AsymmetricCryptography();
		PrivateKey privateKey =  gk.getPrivateKey();
		PublicKey publicKey = gk.getPublicKey();

		String msg = "Cryptography is fun!";
		//String encrypted_msg = ac.encryptText(msg, publicKey);
		String decrypted_msg = new String(ac.decryptText(ac.encryptText(msg, publicKey), privateKey), "UTF-8");
		System.out.println("Original Message: " + msg +
			"\nEncrypted Message: " + msg
			+ "\nDecrypted Message: " + decrypted_msg.toString());
   }

}
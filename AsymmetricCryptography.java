import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;

/**
*Douglas Kaelin
*HW 9 Research
*AsymmetricCryptography uses java Cipher to encrypt and decrypt btye arrays
*Uses RSA which is an encryption that ses public and private keys

@author dek4204
*/

public class AsymmetricCryptography {
	private Cipher cipher;
   /**
   *Constructor creates a cipher with the algorithum RSA
   */
	public AsymmetricCryptography() throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.cipher = Cipher.getInstance("RSA");
	}
   /**
   *Decrypts a message with a private key
   @param message the message to be decrypted
   @param key the private key
   @return message the decrypted message
   */
	public byte[] decryptText(byte[] message, PrivateKey key) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(message);
    }
    /**
   *Encrypts a message with a public key
   @param message the message to be encrypted
   @param key the public key
   @return message the encrypted message
   */
    public byte[] encryptText(String message, PublicKey key) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message.getBytes());
    }
}
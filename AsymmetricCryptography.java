

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;

import org.apache.commons.codec.binary.Base64;

public class AsymmetricCryptography {
	private Cipher cipher;

	public AsymmetricCryptography() throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.cipher = Cipher.getInstance("RSA");
	}

	public byte[] decryptText(byte[] message, PrivateKey key) throws Exception {
        // 16 byte secretKey
        //String secretKey = "TestSecretKey111";
        //SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
        //Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        // return cipher.doFinal(message.getBytes());
        return cipher.doFinal(message);
    }

    public byte[] encryptText(String message, PublicKey key) throws Exception {
        // 16 byte secretKey
        //String secretKey = "TestSecretKey111";
        //SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
        //Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message.getBytes());
    }
}
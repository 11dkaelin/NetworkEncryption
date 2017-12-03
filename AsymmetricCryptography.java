//package com.mkyong.asymmetric;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class AsymmetricCryptography {
	private Cipher cipher;

	public AsymmetricCryptography() throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.cipher = Cipher.getInstance("RSA");
	}

	public PrivateKey getPrivate(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public PublicKey getPublic(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	public String encryptText(String msg, PrivateKey key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
	}

	public String decryptText(String msg, PublicKey key)
			throws InvalidKeyException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
	}


// 	public static void main(String[] args) throws Exception {
// 		AsymmetricCryptography ac = new AsymmetricCryptography();
// 		PrivateKey privateKey = ac.getPrivate("KeyPair/privateKey");
// 		PublicKey publicKey = ac.getPublic("KeyPair/publicKey");
// 
// 		String msg = "Cryptography is fun!";
// 		String encrypted_msg = ac.encryptText(msg, privateKey);
// 		String decrypted_msg = ac.decryptText(encrypted_msg, publicKey);
// 		System.out.println("Original Message: " + msg +
// 			"\nEncrypted Message: " + encrypted_msg
// 			+ "\nDecrypted Message: " + decrypted_msg);
// 
// 		if (new File("KeyPair/text.txt").exists()) {
// 			ac.encryptFile(ac.getFileInBytes(new File("KeyPair/text.txt")),
// 				new File("KeyPair/text_encrypted.txt"),privateKey);
// 			ac.decryptFile(ac.getFileInBytes(new File("KeyPair/text_encrypted.txt")),
// 				new File("KeyPair/text_decrypted.txt"), publicKey);
// 		} else {
// 			System.out.println("Create a file text.txt under folder KeyPair");
// 		}
// 	}
}
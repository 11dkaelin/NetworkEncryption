import java.io.*;
import java.security.*;


/**
*Douglas Kaelin
*HW 9 Research
*GenerateKeys makes public and priavte key to use for encryption and decryption
@author dek4204
*/
public class GenerateKeys {

	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;
   
   /**
   *GenerateKeys constructor takes a key length and sets up the key encryption to use
   
   @param keylength How long you want the key to be
   */
	public GenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyGen = KeyPairGenerator.getInstance("RSA");
		this.keyGen.initialize(keylength);
	}
   
   /**
   *CreateKeys uses java keys to create a jey pair
   *one public and one private
   */
	public void createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}
   /**
   *gets the priavte key
   @return privateKey the priavte key
   */
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}
   /**
   *gets the public key
   @return publicKey the public key
   */
	public PublicKey getPublicKey() {
		return this.publicKey;
	}
}
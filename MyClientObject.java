import java.io.*;
import java.net.*;
import java.security.*;
import javax.swing.*;
import javax.crypto.*;


public class MyClientObject{
   private PrivateKey privateKey;
   private PublicKey publicKey;
   public static void main(String[] args){
      
      MyClientObject c1 = new MyClientObject();
       
            
   }//end main
   
   public MyClientObject(){
      GenerateKeys gk=null;
 		try {
 	      gk = new GenerateKeys(1024);
 		   gk.createKeys();
      }catch (NoSuchAlgorithmException | NoSuchProviderException e) {
 			System.err.println(e.getMessage());
 		}
      privateKey = gk.getPrivateKey();
      publicKey = gk.getPublicKey();
      try{
         Socket s = new Socket("localhost",16789);
         
         ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
         ObjectInputStream in = new ObjectInputStream(s.getInputStream());
        
         //write the public key to the server
         out.writeObject(publicKey);
         out.flush();
         
         //read the server's public key
         PublicKey serverKey = (PublicKey) in.readObject();
         System.out.println("Read server key ");
         
         //ask what you want to send
         String msg = JOptionPane.showInputDialog(null, "What is your message?");
         System.out.println("Message before: " + msg);
         AsymmetricCryptography ac = new AsymmetricCryptography();
		   String encrypted_msg = ac.encryptText(msg, serverKey);
         System.out.println("Message after: " + encrypted_msg);
         
         //send msg
         out.writeObject(encrypted_msg);
         out.flush();
         
         //read encrypted message from server
         String server_encrypted_msg = (String) in.readObject();
         System.out.println("Message recieved from server: " + server_encrypted_msg);
         String decrypted_msg = ac.decryptText(server_encrypted_msg, privateKey);
         System.out.println("Decrypted Message: " + decrypted_msg);

      
         System.out.println();
      }
      catch(UnknownHostException uhe){
         System.out.println("Unknown Server");
      }
      catch(IOException ioe){
         System.out.println("Some IO problem");
         ioe.printStackTrace();
      }
      catch(ArrayIndexOutOfBoundsException ab){
         System.out.println("Say something");
      }
      catch(ClassNotFoundException cnfe){
         System.out.println("Say something");
      }
      catch(NoSuchAlgorithmException nsae){
         System.out.println("Say something");
      }
      catch(NoSuchPaddingException nspe){
         System.out.println("Say something");
      }
      catch(InvalidKeyException ivke){
         System.out.println("Say something");
      }
      catch(IllegalBlockSizeException ibse){
         System.out.println("Say something");
      }
      catch(BadPaddingException bpe){
         System.out.println("Say something");
      }
   }
}// end MyClient
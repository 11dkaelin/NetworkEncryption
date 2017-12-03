import java.io.*;
import java.net.*;
import java.security.*;
import javax.swing.*;
import javax.crypto.*;


public class MyServerObject {
   private PrivateKey privateKey;
   private PublicKey publicKey;
   public static void main(String [] args){
      new MyServerObject();
   }
   
   public MyServerObject(){
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
         //make connection with server
         ServerSocket ss = new ServerSocket(16789);
         
         //get a client connection and a socket
         Socket cs = null;
         
         
         while(true){
            System.out.println(InetAddress.getLocalHost());
            System.out.println("Waiting for a client to connect...");
            cs=ss.accept();
            System.out.println("Have a client connection: " + cs);
               
            ThreadedServer ts = new ThreadedServer(cs);
            ts.start();
         }//end while
      }//and try
      catch(BindException be){
         System.out.println("Server already running");
      }
      catch(IOException ioe){
         ioe.printStackTrace();
      }
   }//end main
   class ThreadedServer extends Thread{
      Socket cs =null;
      
      public ThreadedServer(Socket _cs){
         cs=_cs;
      }
      
      public void run(){
         ObjectInputStream in = null;
         ObjectOutputStream out = null;
         try{
            //open input and Output from the server
            in=new ObjectInputStream(cs.getInputStream());
            out=new ObjectOutputStream(cs.getOutputStream());
            
         
            do{
            //read the clients's public key
               PublicKey clientKey = (PublicKey) in.readObject();
               System.out.println("Read client key: " + clientKey);
               
               
            //write the public key to the client
               out.writeObject(publicKey);
               out.flush();
            
            //read encrypted message from client
               AsymmetricCryptography ac = new AsymmetricCryptography();
               String client_encrypted_msg = (String) in.readObject();
               System.out.println("Message recieved from client: " + client_encrypted_msg);
               String decrypted_msg = ac.decryptText(client_encrypted_msg, privateKey);
               System.out.println("Decrypted Message: " + decrypted_msg);
            
            //ask what you want to send
               String msg = JOptionPane.showInputDialog(null, "What is your message?");
               System.out.println("Message before: " + msg); 
               String encrypted_msg = ac.encryptText(msg, clientKey);
               System.out.println("Message after: " + encrypted_msg);
            
            //send msg
               out.writeObject(encrypted_msg);
               out.flush();
            
            
            }while(in!=null);
            //close everything
            in.close();
            out.close();
            cs.close();
            
            
         
         }//end second try
         catch(EOFException eofe){
         }
         catch(BindException be){
            System.out.println("Server already running");
         }
         catch(IOException ioe){
            ioe.printStackTrace();
         }
         catch(ClassNotFoundException cnfe){
            
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
         
      
      }//end main   
   }//end MyServer        
}
import java.io.*;
import java.net.*;



public class MyServerObject {

   public static void main(String [] args){
      new MyServerObject();
   }
   
   public MyServerObject(){
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
         ObjectInputStream ois = null;
         ObjectOutputStream oos = null;
         try{
            //open input and Output from the server
            ois=new ObjectInputStream(cs.getInputStream());
            oos=new ObjectOutputStream(cs.getOutputStream());
            

            do{
            //read from client
               SalesOrder sales = (SalesOrder) ois.readObject();
               String salesString1 = sales.toString();
               System.out.println("Read: " + salesString1);
            
            //do something with it
               double price =sales.getPrice();
               price=price + (price * 0.1);
               sales.setPrice(price);
               String salesString2 = sales.toString();
               System.out.println("Edited: " + salesString2);
            
            //write to client
               oos.writeObject(sales);
               oos.flush();
            }while(ois!=null);
            //close everything
            ois.close();
            oos.close();
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
         
      
      }//end main   
   }//end MyServer        
}
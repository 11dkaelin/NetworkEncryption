import java.io.*;
import java.net.*;
import java.security.*;

public class MyClientObject{
   
   public static void main(String[] args){
      
      MyClientObject c1 = new MyClientObject();
       
            
   }//end main
   
   public MyClientObject(){
      String name = "Dan";
      double price = 12.00;
      SalesOrder sales = new SalesOrder(name,price);
      try{
         Socket s = new Socket("localhost",16789);
         
         ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
         ObjectInputStream in = new ObjectInputStream(s.getInputStream());
        
         
         out.writeObject(sales);
         SalesOrder sales2 = (SalesOrder)in.readObject();
      
         System.out.println(sales2.toString());
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
      
      }
   
   }
   
   class SalesOrder implements Serializable {
   
      private String itemName;
      private double itemPrice;
   
      public SalesOrder(String name, double price){
         itemName = name;
         itemPrice = price;
      }
   
      public String getName(){
         return itemName;
      }
   
      public void setName(String name){
         itemName = name;
      }
   
      public double getPrice(){
         return itemPrice;
      }
   
      public void setPrice(double price){
         itemPrice = price;
      }
   
      public String toString(){
         return itemName+" cost "+itemPrice;
      }
   }//end SalesOrder
}// end MyClient
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagequeuingserverprocess;

import clientside.notificationinterface;
import java.io.BufferedWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pooja_000
 */
public class notificationfunctions extends UnicastRemoteObject implements notificationinterface{
    //constructor
    public notificationfunctions() throws RemoteException
    {
       
    }
    
            public void notificationdatafrommqsserver() {
         //file creation to save data
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter("arrayData.txt", false));
                    }
                catch (IOException ex) {
                     Logger.getLogger(notificationfunctions.class.getName()).log(Level.SEVERE, null, ex);
                                       }
         //if empty array sleep for 7 seconds- sleep thread
                while(studentfunctions.singlearraystrunctre.size()==0)
                {
                 try { 
                   Thread.sleep(7000);
                   System.out.print("No messages to be sent from Notification");
                     } 
                 catch (InterruptedException ex) 
                 {
                   Logger.getLogger(advisorfunction.class.getName()).log(Level.SEVERE, null, ex);
                 }
                }  
                
                
            // if data in array
              for(int i =0 ; i< studentfunctions.singlearraystrunctre.size() ; i++)
                 {
                     //old data
              if(studentfunctions.singlearraystrunctre.get(i).contains("studentdata"))
                    {
                    }
              else
                 {                           
                   try {
                       //write data to file
                         writer.write(studentfunctions.singlearraystrunctre.get(i));
                         writer.newLine();
                        }
                   catch (IOException ex)
                        {
                         ex.printStackTrace();
                        }
                   System.out.print('\n'+"Notifications to Student"+ '\n');

                   System.out.print( '\n'+studentfunctions.singlearraystrunctre.get(i)+'\n');
                      
//studentfunctions.singlearraystrunctre.removeAll(studentfunctions.singlearraystrunctre);
                 }
                }
                  try {
                      writer.flush();
                      writer.close();
                      System.out.print("Saved Array To File Successfully...");

                      } 
                  catch (IOException ex) 
                     {
                      System.out.print("Couldnt Save Array To File... ");
                      ex.printStackTrace();
                     }
                  System.out.print('\n'+"Performing clear operation !!"+'\n');
                  //clear array after retrival   
                  studentfunctions.singlearraystrunctre.clear();
                  int retval = studentfunctions.singlearraystrunctre.size();
                  System.out.println('\n'+"Now, list consists of "+ retval +" elements");

                 }
                
            }
     
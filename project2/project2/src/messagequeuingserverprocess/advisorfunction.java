/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagequeuingserverprocess;

import clientside.advisorinterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pooja_000
 */
public class advisorfunction extends UnicastRemoteObject implements advisorinterface{
//constructor
     public advisorfunction() throws RemoteException
    {
       
    }
    @Override
    public void advisordatafrommqsserver() {
       //System.out.println("("+ studentfunctions.singlearraystrunctre);
       // if array null- sleep thread for 3 seconds
            while(studentfunctions.singlearraystrunctre.size()==0)
            {
               try { 
                   Thread.sleep(3000);
                   System.out.print("Advisor got no data to approve or disapprove");
                   }
               catch (InterruptedException ex) 
               {
                   Logger.getLogger(advisorfunction.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
            
          
            
              //size of array- contents in array then
              for(int i =0 ; i< studentfunctions.singlearraystrunctre.size() ; i++)
            {
              //check for keyword studentdata
            if(studentfunctions.singlearraystrunctre.get(i).contains("studentdata"))
              {
                //Random assignment of Approval Disapproval
                  Random rand = new Random();
                  String[] arr =  studentfunctions.singlearraystrunctre.get(i).split(":");
                  if(rand.nextBoolean())
                   {
                  
                  studentfunctions.singlearraystrunctre.set(i,"Approval: True"+""+arr[1]);
              //studentfunctions.singlearraystrunctre.get(i).replace("studentdata", "Flag : True");
                   }
                  else
                   {
                  studentfunctions.singlearraystrunctre.set(i, "Approval: False"+""+arr[1]);
              //studentfunctions.singlearraystrunctre.get(i).replace("studentdata", "Flag : False");
                   }
                System.out.print('\n'+"Advisor Approval/Disapproval");
                //updated array with approval disapproval
                System.out.println( '\n'+studentfunctions.singlearraystrunctre.get(i)+'\n');
              
              } 
            else
                {
                    System.out.println("No new data is availble for advisor to display!");
                } 
            
             }
       }
    }



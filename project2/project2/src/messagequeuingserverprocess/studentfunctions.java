/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagequeuingserverprocess;


import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import clientside.studentinterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pooja_000
 */
public class studentfunctions extends UnicastRemoteObject implements studentinterface {
Scanner s;
 //list- string array
 //https://www.javatpoint.com/java-arraylist
public static List<String> singlearraystrunctre = new ArrayList<>();
//construtor
    public studentfunctions() throws RemoteException, FileNotFoundException
    {
       
    }
    
    
    @Override
    public boolean senddatatomqsserver(String name, String course) {    
       
try
			{
			File f;
		    f=new File("arrayData.txt");
		    if(!f.exists())
		    f.createNewFile();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
	
          //retrieve data from file
        try {
            //http://www.dreamincode.net/forums/topic/278544-reading-data-from-a-file-into-an-array/
                this.s = new Scanner(new File("arrayData.txt"));
                while (s.hasNext()){
                                     singlearraystrunctre.add(s.next());
                                   }
                 s.close();
            }
        catch (FileNotFoundException ex)
        {
        Logger.getLogger(studentfunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

//        System.out.println(name, course);
    boolean found=false;
  //if user entered data     
    try{
       if (name!=null && course!=null){
        found=true;
        singlearraystrunctre.add("studentdata:"+name+""+course);
        System.out.print('\n'+"Student Data"+ '\n');
        System.out.print('\n'+""+singlearraystrunctre);

        }
      else
        {
        found=false;}
       }
       catch(Exception ex){
           ex.printStackTrace();
               }
        return found;

    
    }
    
}

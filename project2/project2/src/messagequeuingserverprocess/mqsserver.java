/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagequeuingserverprocess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pooja_000
 */
public class mqsserver {
    public static void main (String []args) throws FileNotFoundException {
        try {
//Registry same port for connection of server and client
           Registry reg=LocateRegistry.createRegistry(1099);
   //student calling       
           studentfunctions sfunc=new studentfunctions();
            reg.rebind("stud", sfunc);
// advisor process calling
           advisorfunction afunc=new advisorfunction();
           reg.rebind("advi", afunc);
//notification process calling
           notificationfunctions nfunc=new notificationfunctions();
           reg.rebind("notif", nfunc);

            System.out.print("Server running");
            
            } 
        catch (RemoteException ex) {
            Logger.getLogger(mqsserver.class.getName()).log(Level.SEVERE, null, ex);
                                   }
    
    }
}

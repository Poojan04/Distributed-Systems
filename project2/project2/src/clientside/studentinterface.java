/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientside;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author pooja_000
 */
public interface studentinterface extends Remote{
    
    /**
     *
     * @param name
     * @param course
     * @return
     */
    public boolean senddatatomqsserver(String name , String course) throws RemoteException;
    
}

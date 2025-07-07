package BRIAN.JOEL.JavaRMIBackEnd.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Compute extends Remote {
    <T> T executeTask(Task<T> t) throws RemoteException;
}
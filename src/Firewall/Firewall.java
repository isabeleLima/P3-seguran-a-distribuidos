package Firewall;

import Entity.Header;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Firewall extends Remote {
    ArrayList protocols = new ArrayList<Header>();

    boolean isPermited(Header header) throws RemoteException;
}

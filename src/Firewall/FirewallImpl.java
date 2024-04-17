package Firewall;

import Entity.Header;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

    public class FirewallImpl extends UnicastRemoteObject implements Firewall {

    public FirewallImpl() throws RemoteException {
        super();
        protocols.add(new Header("user", "server", "auth"));

        protocols.add(new Header("client", "server", "listCars"));
        protocols.add(new Header("client", "server", "findCarByRenavam"));
        protocols.add(new Header("client", "server", "buyCar"));

        protocols.add(new Header("funcionario", "server", "listCars"));
        protocols.add(new Header("funcionario", "server", "addCar"));
        protocols.add(new Header("funcionario", "server", "findCarByRenavam"));
        protocols.add(new Header("funcionario", "server", "removeCar"));
        protocols.add(new Header("funcionario", "server", "getNumberOfCars"));
    }

    @Override
    public boolean isPermited(Header header) throws RemoteException {
        System.out.println("HEADER: " + header.toString());
        System.out.println("PROTOCOLO 0: " + protocols.get(0).toString());
        for (Object protocol:protocols) {
            if (header.toString().equals(protocol.toString())){
                return true;
            }
        }

        return false;
    }
}

package Client;

import Services.CarService;
import Services.CarServiceImpl;
import Services.SincService;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server2 extends UnicastRemoteObject {
    protected Server2() throws RemoteException {
        super();

    }

    public static void main(String[] args) {
        try {

            LocateRegistry.createRegistry(1095);

            SincService sincService = (SincService) Naming.lookup("//localhost:1093/Sincronizacao");
            String adress = "//localhost:1095/CarService2";
            CarService carService2 = new CarServiceImpl(sincService,adress);

            Naming.rebind(adress, carService2);
                try {
                    Registry registry = LocateRegistry.getRegistry("localhost", 1097);
                    if (registry != null) {
                        String[] boundNames = registry.list();
                        for (String name : boundNames) {
                            System.out.println("Serviço registrado: " + name);
                        }
                    }
                } catch (RemoteException e) {
                    System.err.println("Não foi possível conectar ao registro RMI: " + e.getMessage());
                    Thread.sleep(5000);
                }
            System.out.println("Servidor RMI de carros pronto.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

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

public class Server1 extends UnicastRemoteObject {

    protected Server1() throws RemoteException {
        super();

    }

    public static void main(String[] args) {
        try {

            LocateRegistry.createRegistry(1097);
            SincService sincService = (SincService) Naming.lookup("//localhost:1093/Sincronizacao");
            String adress = "//localhost:1097/CarService";
            CarService carService = new CarServiceImpl(sincService,adress);
            Naming.rebind(adress, carService);

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
                    Thread.sleep(5000); // Espera 1 segundo antes de tentar novamente

                }
            System.out.println("Servidor RMI de carros pronto.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

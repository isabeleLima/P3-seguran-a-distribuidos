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
            // Cria o registro RMI na porta 1097
            LocateRegistry.createRegistry(1097);

            //busca o service de sincronização para criar o service de carros
            SincService sincService = (SincService) Naming.lookup("//localhost:1093/Sincronizacao");

            String adress = "//localhost:1097/CarService";
            CarService carService = new CarServiceImpl(sincService,adress);

            Naming.rebind(adress, carService);

            boolean start = false;
            while (!start) {
                try {
                    Registry registry = LocateRegistry.getRegistry("localhost", 1097);
                    if (registry != null) {
                        String[] boundNames = registry.list();
                        for (String name : boundNames) {
                            System.out.println("Serviço registrado: " + name);
                        }
                        try {
                            CarService carService2 = (CarService) Naming.lookup("//localhost:1095/CarService2");
                            start = true; // Se o serviço for encontrado, saia do loop

                        } catch (NotBoundException e) {
                            Thread.sleep(5000);

                            System.out.println("Aguardando o registro do serviço CarService...");
                        }
                    }
                } catch (RemoteException e) {
                    System.err.println("Não foi possível conectar ao registro RMI: " + e.getMessage());
                    Thread.sleep(5000); // Espera 1 segundo antes de tentar novamente

                }

            }
            System.out.println("Servidor RMI de carros pronto.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

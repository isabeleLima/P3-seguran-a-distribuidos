package Client;

import Services.CarService;
import Services.SincServiceImpl;
import Services.SincService;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class SincServer extends UnicastRemoteObject {

    protected SincServer() throws RemoteException {
        super();

    }

    public static void main(String[] args) {
        try {
            //Iniciando o servidor de sincronização
            LocateRegistry.createRegistry(1093);
            SincService sincService = new SincServiceImpl();
            Naming.rebind("//localhost:1093/Sincronizacao", sincService);


            boolean start = false;
            while (!start) {
                try {
                    Registry registry = LocateRegistry.getRegistry("localhost", 1093);
                    if (registry != null) {
                        String[] boundNames = registry.list();
                        for (String name : boundNames) {
                            System.out.println("Serviço registrado: " + name);
                            start = true;
                        }

                    }
                } catch (RemoteException e) {
                    System.err.println("Não foi possível conectar ao registro RMI: " + e.getMessage());
                    Thread.sleep(5000); // Espera 1 segundo antes de tentar novamente

                    // e.printStackTrace();
                }

            } // Adiciona cada serviço como servidor um do outro para sincronização
            System.out.println("Servidor RMI de carros pronto.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

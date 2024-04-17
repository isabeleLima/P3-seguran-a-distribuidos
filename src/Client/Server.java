package Client;
import Firewall.Firewall;
import Firewall.FirewallImpl;
import Gateway.GatewayImpl;
import Services.AuthService;
import Services.AuthServiceImpl;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;

import Gateway.Gateway;
import Services.CarService;

public class Server {
    public static void main(String[] args) {
        try {
            // Obtendo o endereço IP do servidor
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            // Obtendo a data e hora atuais
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = dateFormat.format(new Date());

            // Criando e vinculando o servidor de autenticação
            AuthService authenticationServer = new AuthServiceImpl();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("AuthenticationServer", authenticationServer);

            // Criando e vinculando o servidor de autenticação
            Firewall firewallServer = new FirewallImpl();
            LocateRegistry.createRegistry(1120);
            Naming.rebind("FirewallServer", firewallServer);


            boolean serviceReady = false;
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
                e.printStackTrace();
            }
            while (!serviceReady) {
                try {
                    //buscando os dois services para o gateway
                    CarService carService1 = (CarService) Naming.lookup("//localhost:1097/CarService");

                    CarService carService2 = (CarService) Naming.lookup("//localhost:1095/CarService2");

                    // Criando e vinculando o gateway adicionando o auth e o service do car
                    Gateway gateway = new GatewayImpl(authenticationServer, carService1, carService2, firewallServer);

                    Naming.rebind("Gateway", gateway);
                    serviceReady = true; // Se o serviço for encontrado, saia do loop
                } catch (NotBoundException e) {
                    System.out.println("Aguardando o registro do serviço CarService...");
                    Thread.sleep(1000); // Espera 1 segundo antes de tentar novamente
                }
            }

            // Imprimindo informações do servidor
            System.out.println("Server started at " + currentTime);
            System.out.println("IP Address: " + ipAddress);

            System.out.println("Server ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}

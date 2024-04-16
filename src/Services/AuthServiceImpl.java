package Services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import Entity.Client;
public class AuthServiceImpl extends UnicastRemoteObject implements AuthService{
    private List<Client> clientList;
    public AuthServiceImpl() throws RemoteException {
        super();
        this.clientList = new ArrayList<>();

        clientList.add(new Client("Cliente maria", "user1", "123", "cliente"));
        clientList.add(new Client("Funcionario Jose", "user2", "123", "funcionario"));
    }
    @Override
    public String authenticate(String username, String password) throws RemoteException {
        for (Client client : clientList) {
            if (client.getUser().equals(username) && client.getSenha().equals(password)) {
                return client.getTipo();
            }
        }
        return "Cliente não encontrado";
    }
}

package Gateway;

import Entity.Car;
import Entity.Header;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Gateway extends Remote {
    String getCarInfo(String renavan, Header header) throws RemoteException, InterruptedException;

    boolean addCar(Car car, Header header) throws Exception;

    List<String> listCars(Header header) throws Exception;

    String removeCar(String renavan, Header header) throws Exception;

    String findCarByRenavan(String renavan, Header header) throws Exception;

    int getNumberOfCars(Header header) throws Exception;

    String buyCar(String renavan, Header header) throws Exception;

    String authenticate(String username, String password, Header header) throws RemoteException;
}

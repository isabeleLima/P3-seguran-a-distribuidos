package Gateway;

import Entity.Car;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Gateway extends Remote {
    String getCarInfo(String renavan) throws RemoteException, InterruptedException;

    boolean addCar(Car car) throws RemoteException, InterruptedException;

    List<String> listCars() throws RemoteException, InterruptedException;

    String removeCar(String renavan) throws RemoteException, InterruptedException;

    String findCarByRenavan(String renavan) throws RemoteException, InterruptedException;

    int getNumberOfCars() throws RemoteException, InterruptedException;

    String buyCar(String renavan) throws RemoteException, InterruptedException;

    String authenticate(String username, String password) throws RemoteException;
}

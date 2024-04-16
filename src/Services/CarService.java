package Services;

import Entity.Car;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CarService extends Remote {
    String getCarInfo(String renavan) throws RemoteException, InterruptedException;

    boolean addCar(Car car) throws RemoteException, InterruptedException;

    List<String> listCars() throws RemoteException, InterruptedException;

    String removeCar(String renavan) throws RemoteException, InterruptedException;

    String findCarByRenavan(String renavan) throws RemoteException, InterruptedException;

    int getNumberOfCars() throws RemoteException, InterruptedException;

    String buyCar(String renavan) throws RemoteException, InterruptedException;

    List<Car> getSincronized(SincService server) throws RemoteException, InterruptedException;

    void setSrinconized(SincService server) throws RemoteException, InterruptedException;

    String getAdress()throws RemoteException;

    boolean isOcupado() throws RemoteException;

    void setOcupado(boolean ocupado) throws RemoteException;

}

package Services;

import Entity.Car;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public interface SincService extends Remote {

    List<Car> returnCars(CarService service) throws RemoteException;

    boolean SincCars(List<Car> cars,CarService service)throws RemoteException;

    void AddToQueue(CarService service)throws RemoteException;

    void removeFromQueue()throws RemoteException;


}

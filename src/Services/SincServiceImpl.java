package Services;

import Entity.Car;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SincServiceImpl extends UnicastRemoteObject implements SincService{

    public List<Car> carList;


    Queue<CarService> queue;

    public SincServiceImpl() throws RemoteException {
        super();
        this.carList = new ArrayList<>();
        this.queue = new LinkedList<>();
        carList.add(new Car("000001","fiat", "economico",2024,5,20000));
        carList.add(new Car("000002","hb20", "economico",2024,5,20000));
        carList.add(new Car("000003","toyota", "economico",2024,5,20000));
    }

    @Override
    public List<Car> returnCars(CarService service) throws RemoteException {
        CarService primeiroDaFila = queue.peek();

        if(primeiroDaFila.equals(service)){
            return carList;
        }
        return null;
    }

    @Override
    public boolean SincCars(List<Car> cars,CarService service) throws RemoteException {
        CarService primeiroDaFila = queue.peek();

        if(primeiroDaFila.equals(service)){
            this.carList = cars;
            return true;
        }
        return false;
    }

    public void AddToQueue(CarService service){
        try{
            System.out.println("Adicionando servidor" + service.getAdress() +  " a fila");
            queue.offer(service);
        }catch (Exception e) {
            System.err.println("Server.Car.Client exception: " + e.toString());
            e.printStackTrace();
        }

    }

    public void removeFromQueue(){
        try{
            CarService removed = queue.poll();
            System.out.println("Removendo servidor" + removed.getAdress() +  " da fila");
        }catch (Exception e) {
            System.err.println("Server.Car.Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

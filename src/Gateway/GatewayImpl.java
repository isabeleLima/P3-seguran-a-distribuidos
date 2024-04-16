package Gateway;

import Entity.Car;
import Services.AuthService;
import Services.CarService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Random;

public class GatewayImpl extends UnicastRemoteObject implements Gateway{
    private AuthService authService;

    private CarService carService1;
    private CarService carService2;

    public GatewayImpl(AuthService auth, CarService carService1,CarService carService2) throws RemoteException {
        super();
        this.authService = auth;
        this.carService1 = carService1;
        this.carService2 = carService2;
    }

    @Override
    public String authenticate(String username, String password) throws RemoteException {
        return authService.authenticate(username, password);
    }

    @Override
    public String getCarInfo(String renavan) throws RemoteException, InterruptedException {
       CarService carService = returnService();
       return carService.getCarInfo(renavan);
    }

    @Override
    public boolean addCar(Car car) throws RemoteException, InterruptedException {
        CarService carService = returnService();
        return carService.addCar(car);
    }

    @Override
    public List<String> listCars() throws RemoteException, InterruptedException {
        CarService carService = returnService();
        return carService.listCars();
    }

    @Override
    public String removeCar(String renavan) throws RemoteException, InterruptedException {
        CarService carService = returnService();
        return carService.removeCar(renavan);
    }

    @Override
    public String findCarByRenavan(String renavan) throws RemoteException, InterruptedException {
        CarService carService = returnService();
        return carService.findCarByRenavan(renavan);
    }

    @Override
    public int getNumberOfCars() throws RemoteException, InterruptedException {
        CarService carService = returnService();
        return carService.getNumberOfCars();
    }

    @Override
    public String buyCar(String renavan) throws RemoteException, InterruptedException {
        CarService carService = returnService();
       return carService.buyCar(renavan);
    }

    public CarService returnService() throws RemoteException {
        if(!carService1.isOcupado()){
            carService1.setOcupado(true);
            return carService1;
        }


        if(carService1.isOcupado() && !carService2.isOcupado()){
            carService2.setOcupado(true);
            return carService2;
        }

        return null;
    }
}

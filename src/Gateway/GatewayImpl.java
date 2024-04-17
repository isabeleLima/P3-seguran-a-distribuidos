package Gateway;

import Entity.Car;
import Entity.Header;
import Firewall.Firewall;
import Services.AuthService;
import Services.CarService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GatewayImpl extends UnicastRemoteObject implements Gateway{
    private AuthService authService;
    private CarService carService1;
    private CarService carService2;
    private Firewall firewall;
    Long timeout = 0L;
    int errorCounter = 0;
    Long tempoRestante = 0L;

    public GatewayImpl(AuthService auth, CarService carService1,CarService carService2, Firewall firewall) throws RemoteException {
        super();
        this.authService = auth;
        this.carService1 = carService1;
        this.carService2 = carService2;
        this.firewall = firewall;
    }

    @Override
    public String authenticate(String username, String password, Header header) throws RemoteException {
        if (!firewall.isPermited(header)){
            return "AÇÃO BLOQUEADA POR FIREWALL!";
        }

        Date data = new Date();
        if (data.getTime() > timeout && errorCounter >= 8) {
            errorCounter = 0;
        }

        if (errorCounter >= 8) {
            Date now = new Date();
            Long tempoRestante = timeout - now.getTime();
            return "Muitas tentativas de login. Aguarde " + tempoRestante/1000 + " segundos.";
        }

        if(authService.authenticate(username, password).equals("Cliente não encontrado")){
            errorCounter++;
            if (errorCounter == 8) {
                timeout = data.getTime() + 30000;
            }
            return authService.authenticate(username, password);
        }else{
            return authService.authenticate(username, password);
        }
    }

    @Override
    public String getCarInfo(String renavan, Header header) throws RemoteException, InterruptedException {
        if (!firewall.isPermited(header)){
            return "AÇÃO BLOQUEADA POR FIREWALL!";
        }

       CarService carService = returnService();
        if(carService !=null){
            return carService.getCarInfo(renavan);
        }
       return "Servidores ocupados tente novamente mais tarde";
    }

    @Override
    public boolean addCar(Car car, Header header) throws Exception {
        if (!firewall.isPermited(header)){
            throw new Exception("AÇÃO BLOQUEADA POR FIREWALL");
        }

        CarService carService = returnService();

        if(carService !=null){
            return carService.addCar(car);
        }
        return false;
    }

    @Override
    public List<String> listCars(Header header) throws Exception {
        if (!firewall.isPermited(header)){
            throw new Exception("AÇÃO BLOQUEADA POR FIREWALL");
        }
        CarService carService = returnService();
        return carService.listCars();
    }

    @Override
    public String removeCar(String renavan, Header header) throws Exception {
        if (!firewall.isPermited(header)){
            throw new Exception("AÇÃO BLOQUEADA POR FIREWALL");
        }
        CarService carService = returnService();
        return carService.removeCar(renavan);
    }

    @Override
    public String findCarByRenavan(String renavan, Header header) throws Exception {
        if (!firewall.isPermited(header)){
            throw new Exception("AÇÃO BLOQUEADA POR FIREWALL");
        }
        CarService carService = returnService();
        return carService.findCarByRenavan(renavan);
    }

    @Override
    public int getNumberOfCars(Header header) throws Exception {
        if (!firewall.isPermited(header)){
            throw new Exception("AÇÃO BLOQUEADA POR FIREWALL");
        }
        CarService carService = returnService();
        return carService.getNumberOfCars();
    }

    @Override
    public String buyCar(String renavan, Header header) throws Exception {
        if (!firewall.isPermited(header)){
            throw new Exception("AÇÃO BLOQUEADA POR FIREWALL");
        }
        CarService carService = returnService();
       return carService.buyCar(renavan);
    }

    public CarService returnService() throws RemoteException {
        if(carService1 !=null){
            if(!carService1.isOcupado()){
                carService1.setOcupado(true);
                return carService1;
            }

            if(carService2 !=null){
                if(carService1.isOcupado() && !carService2.isOcupado()){
                    carService2.setOcupado(true);
                    return carService2;
                }
            }
        }
        return null;
    }
}
